package com.premium.aboutyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.premium.aboutyou.data.Language
import com.premium.aboutyou.data.Prefs
import com.premium.aboutyou.data.Strings
import com.premium.aboutyou.ui.screens.BirthdayScreen
import com.premium.aboutyou.ui.screens.HomeScreen
import com.premium.aboutyou.ui.screens.NameScreen
import com.premium.aboutyou.ui.screens.SettingsScreen
import com.premium.aboutyou.ui.theme.AboutYouTheme
import com.premium.aboutyou.ui.theme.AppColors
import java.time.LocalDateTime

/** The four destinations of the bottom bar, each with its own screen. */
private enum class Tab(val icon: ImageVector) {
    Home(Icons.Filled.Home),
    Name(Icons.Filled.Person),
    Birthday(Icons.Filled.Cake),
    Settings(Icons.Filled.Settings)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.init(this)
        enableEdgeToEdge()
        setContent {
            val vm: MainViewModel = viewModel()
            val language by vm.language.collectAsState()
            AboutYouTheme(language = language) {
                Surface(color = AppColors.Bg) {
                    AppRoot(vm)
                }
            }
        }
    }
}

@Composable
private fun AppRoot(vm: MainViewModel) {
    val language by vm.language.collectAsState()
    val strings = vm.strings
    val adsEnabled = remember { mutableStateOf(Prefs.adsEnabled) }

    // Home is the only tab that shows the entry form; the others read from it.
    var tab by remember { mutableStateOf(Tab.Home) }
    var history by remember { mutableStateOf(listOf<Tab>()) }

    fun goTo(next: Tab) {
        if (next == tab) return
        history = history + tab
        tab = next
    }

    // Back walks through the tabs instead of closing the app, and only leaves
    // the app from Home with nothing left to go back to.
    BackHandler(enabled = history.isNotEmpty() || tab != Tab.Home) {
        if (history.isNotEmpty()) {
            tab = history.last()
            history = history.dropLast(1)
        } else {
            tab = Tab.Home
        }
    }

    // ---- Form state, restored from the last session ----
    var name by remember { mutableStateOf(Prefs.lastName) }
    var day by remember { mutableStateOf(Prefs.dobDay) }
    var month by remember { mutableStateOf(Prefs.dobMonth) }
    var year by remember { mutableStateOf(Prefs.dobYear) }
    var hour by remember { mutableStateOf(Prefs.dobHour) }
    var minute by remember { mutableStateOf(Prefs.dobMinute) }

    fun analyze() {
        vm.searchName(name)
        val d = day.toIntOrNull()
        val mo = month.toIntOrNull()
        val y = year.toIntOrNull()
        if (d != null && mo != null && y != null &&
            d in 1..31 && mo in 1..12 && y in 1900..2100
        ) {
            val h = hour.toIntOrNull()?.coerceIn(0, 23) ?: 12
            val mi = minute.toIntOrNull()?.coerceIn(0, 59) ?: 0
            val date = runCatching { java.time.LocalDate.of(y, mo, d) }.getOrNull()
            if (date != null) {
                val dt = date.atTime(h, mi)
                vm.setBirthDateTime(dt)
                vm.loadAllBirths(date)
                persistForm(name, day, month, year, hour, minute)
                goTo(Tab.Birthday)
                return
            }
        }
        persistForm(name, day, month, year, hour, minute)
        goTo(Tab.Name)
    }

    val contentPadding = WindowInsets.systemBars.asPaddingValues()
    val bottomInset = contentPadding.calculateBottomPadding()
    // Space the content leaves free at the bottom: the bar plus the system inset.
    val listPadding = PaddingValues(
        top = contentPadding.calculateTopPadding(),
        bottom = bottomInset + 96.dp
    )

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = tab,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "tab"
        ) { current ->
            when (current) {
                Tab.Home -> HomeScreen(
                    strings = strings,
                    language = language,
                    name = name,
                    onNameChange = { name = it },
                    day = day, month = month, year = year,
                    hour = hour, minute = minute,
                    onDay = { day = it }, onMonth = { month = it }, onYear = { year = it },
                    onHour = { hour = it }, onMinute = { minute = it },
                    onLanguageChange = { vm.setLanguage(it) },
                    onAnalyze = { analyze() },
                    adsEnabled = adsEnabled.value,
                    adLabel = strings.adPlaceholder,
                    contentPadding = listPadding
                )

                Tab.Name -> NameScreen(vm = vm, contentPadding = listPadding)

                Tab.Birthday -> BirthdayScreen(
                    vm = vm,
                    contentPadding = listPadding,
                    adsEnabled = adsEnabled.value,
                    adLabel = strings.adPlaceholder
                )

                Tab.Settings -> SettingsScreen(
                    vm = vm,
                    language = language,
                    onLanguageChange = { vm.setLanguage(it) },
                    adsEnabled = adsEnabled.value,
                    onAdsChange = { adsEnabled.value = it; Prefs.adsEnabled = it },
                    contentPadding = listPadding
                )
            }
        }

        BottomBar(
            tab = tab,
            strings = strings,
            onSelect = { goTo(it) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomInset)
        )
    }
}

@Composable
private fun BottomBar(
    tab: Tab,
    strings: Strings,
    onSelect: (Tab) -> Unit,
    modifier: Modifier = Modifier
) {
    val labels = mapOf(
        Tab.Home to strings.tabHome,
        Tab.Name to strings.tabName,
        Tab.Birthday to strings.tabBirthday,
        Tab.Settings to strings.tabSettings
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(AppColors.Surface.copy(alpha = 0.97f))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Tab.entries.forEach { entry ->
            val selected = entry == tab
            val tint = if (selected) AppColors.Gold else AppColors.Muted
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selected) AppColors.Gold.copy(alpha = 0.10f) else Color.Transparent)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    entry.icon,
                    contentDescription = labels[entry],
                    tint = tint,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    labels[entry] ?: "",
                    color = tint,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            }
        }
    }
}

private fun persistForm(
    name: String, day: String, month: String, year: String, hour: String, minute: String
) {
    Prefs.lastName = name
    Prefs.dobDay = day
    Prefs.dobMonth = month
    Prefs.dobYear = year
    Prefs.dobHour = hour
    Prefs.dobMinute = minute
}
