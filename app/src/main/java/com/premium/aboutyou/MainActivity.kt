package com.premium.aboutyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.premium.aboutyou.ui.components.GlassCard
import com.premium.aboutyou.ui.screens.AgeScreen
import com.premium.aboutyou.ui.screens.BirthdayScreen
import com.premium.aboutyou.ui.screens.HomeScreen
import com.premium.aboutyou.ui.screens.NameScreen
import com.premium.aboutyou.ui.theme.AboutYouTheme
import com.premium.aboutyou.ui.theme.AppColors
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private enum class Screen { Home, Name, Age, Birthday }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AboutYouTheme {
                Surface(color = AppColors.Bg) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
private fun AppRoot() {
    val vm: MainViewModel = viewModel()
    var screen by remember { mutableStateOf(Screen.Home) }
    var showDatePicker by remember { mutableStateOf(false) }
    val birthDateTime by vm.birthDateTime.collectAsState()

    val dateLabel = birthDateTime?.let {
        it.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (screen) {
            Screen.Home -> HomeScreen(
                onSearchName = { name ->
                    vm.searchName(name)
                    screen = Screen.Name
                },
                onOpenDatePicker = { showDatePicker = true },
                selectedDateLabel = dateLabel
            )

            Screen.Name -> NameScreen(vm = vm, onBack = { screen = Screen.Home })

            Screen.Age -> {
                val bd = birthDateTime
                if (bd == null) {
                    LaunchedEffect(Unit) { screen = Screen.Home }
                } else {
                    AgeScreen(birth = bd, onBack = { screen = Screen.Home })
                }
            }

            Screen.Birthday -> BirthdayScreen(vm = vm, onBack = { screen = Screen.Home })
        }
    }

    if (showDatePicker) {
        BirthDateTimeDialog(
            onDismiss = { showDatePicker = false },
            onConfirm = { dt ->
                vm.setBirthDateTime(dt)
                vm.loadAllBirths(dt.toLocalDate())
                showDatePicker = false
                screen = Screen.Age
            }
        )
    }

    // Once a date is chosen the user can jump to the birthday list.
    if (birthDateTime != null && screen == Screen.Age) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 22.dp)
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { screen = Screen.Birthday },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Violet,
                    contentColor = AppColors.Text
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text("See who shares my birthday", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDateTimeDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDateTime) -> Unit
) {
    val pickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().minusYears(25).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        yearRange = 1900..LocalDate.now().year
    )
    var hour by remember { mutableStateOf(12) }
    var minute by remember { mutableStateOf(0) }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val millis = pickerState.selectedDateMillis
                if (millis != null) {
                    val date = java.time.Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.of("UTC")).toLocalDate()
                    onConfirm(date.atTime(hour, minute))
                }
            }) { Text("Confirm", color = AppColors.Gold, fontWeight = FontWeight.Bold) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = AppColors.Muted) }
        },
        colors = DatePickerDefaults.colors(
            containerColor = AppColors.Surface,
            titleContentColor = AppColors.Text,
            headlineContentColor = AppColors.Text,
            weekdayContentColor = AppColors.Muted,
            dayContentColor = AppColors.Text,
            selectedDayContainerColor = AppColors.Gold,
            selectedDayContentColor = AppColors.Bg,
            todayContentColor = AppColors.Gold,
            todayDateBorderColor = AppColors.Gold
        )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 8.dp)
        ) {
            DatePicker(state = pickerState)
            Spacer(Modifier.height(8.dp))
            GlassCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                accent = AppColors.Cyan,
                contentPadding = PaddingValues(14.dp)
            ) {
                Text(
                    "Add your time of birth (optional)",
                    color = AppColors.Text,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "A precise time makes the live counter and star sign more exact.",
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TimeStepper("Hour", hour, 0, 23) { hour = it }
                    Spacer(Modifier.width(16.dp))
                    TimeStepper("Minute", minute, 0, 59) { minute = it }
                }
            }
        }
    }
}

@Composable
private fun TimeStepper(label: String, value: Int, min: Int, max: Int, onChange: (Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            FilledIconButton(onClick = { onChange(if (value <= min) max else value - 1) }) {
                Text("−", color = AppColors.Text)
            }
            Text(
                "%02d".format(value),
                color = AppColors.Gold,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            FilledIconButton(onClick = { onChange(if (value >= max) min else value + 1) }) {
                Text("+", color = AppColors.Text)
            }
        }
    }
}
