package com.premium.aboutyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.premium.aboutyou.LoadState
import com.premium.aboutyou.MainViewModel
import com.premium.aboutyou.data.BirthEntry
import com.premium.aboutyou.data.Language
import com.premium.aboutyou.data.Strings
import com.premium.aboutyou.data.Zodiac
import com.premium.aboutyou.data.localized
import com.premium.aboutyou.net.Repo
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors
import com.premium.aboutyou.util.AgeCalc
import kotlinx.coroutines.delay
import java.time.LocalDateTime

/**
 * The Birthday tab holds everything that follows from the date of birth: the
 * live counter, the totals, the star sign and the famous people born on the
 * same day and on the exact same date.
 */
@Composable
fun BirthdayScreen(vm: MainViewModel, contentPadding: PaddingValues, adsEnabled: Boolean, adLabel: String) {
    val birth by vm.birthDateTime.collectAsState()
    val dayState by vm.dayBirths.collectAsState()
    val exactState by vm.exactBirths.collectAsState()
    val language by vm.language.collectAsState()
    val strings = vm.strings

    if (birth == null) {
        AppBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(18.dp))
                ScreenHeader(strings.birthTitle, accent = AppColors.Pink)
                Spacer(Modifier.height(16.dp))
                GlassCard(accent = AppColors.Pink) {
                    EmptyBlock(strings.enterDobFirst)
                }
            }
        }
        return
    }

    var now by remember { mutableStateOf(LocalDateTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = LocalDateTime.now()
            delay(1000)
        }
    }

    val b = birth!!
    val parts = remember(b, now) { AgeCalc.compute(b, now) }
    val zodiac = remember(b) { Zodiac.forDate(b.toLocalDate()) }
    val z = remember(zodiac, language) { zodiac.localized(language) }

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Spacer(Modifier.height(18.dp))
                ScreenHeader(
                    title = strings.birthTitle,
                    subtitle = Repo.fullDate(b.toLocalDate(), language),
                    accent = AppColors.Pink
                )
                Spacer(Modifier.height(16.dp))
            }

            // ---- Birth stamp ----
            item {
                GlassCard(accent = AppColors.Gold) {
                    SectionHeader(Icons.Filled.Cake, strings.bornOn)
                    Spacer(Modifier.height(10.dp))
                    GradientText(
                        text = Repo.fullDate(b.toLocalDate(), language),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${strings.atTime} ${"%02d:%02d".format(b.hour, b.minute)} • ${Repo.weekday(b.toLocalDate(), language)}",
                        color = AppColors.Muted,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(14.dp))
            }

            // ---- Live counter ----
            item {
                GlassCard(accent = AppColors.Cyan) {
                    SectionHeader(
                        Icons.Filled.Bolt,
                        strings.liveCounter,
                        strings.liveSub,
                        AppColors.Cyan
                    )
                    Spacer(Modifier.height(16.dp))
                    TimerRow(
                        listOf(
                            Triple(strings.years, parts.years, AppColors.Gold),
                            Triple(strings.months, parts.months, AppColors.Violet),
                            Triple(strings.days, parts.days, AppColors.Cyan)
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    TimerRow(
                        listOf(
                            Triple(strings.hours, parts.hours, AppColors.Pink),
                            Triple(strings.minutes, parts.minutes, AppColors.Blue),
                            Triple(strings.seconds, parts.seconds, AppColors.Green)
                        )
                    )
                }
                Spacer(Modifier.height(14.dp))
            }

            // ---- Totals ----
            item {
                GlassCard(accent = AppColors.Violet) {
                    SectionHeader(Icons.Filled.Summarize, strings.totalTime, accent = AppColors.Violet)
                    Spacer(Modifier.height(14.dp))
                    StatTile(strings.totalMonths, "%,d".format(parts.totalMonths), AppColors.Violet)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatTile(strings.totalWeeks, "%,d".format(parts.totalWeeks), AppColors.Cyan, Modifier.weight(1f))
                        StatTile(strings.totalDays, "%,d".format(parts.totalDays), AppColors.Gold, Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatTile(strings.totalHours, "%,d".format(parts.totalHours), AppColors.Pink, Modifier.weight(1f))
                        StatTile(strings.totalMinutes, "%,d".format(parts.totalMinutes), AppColors.Blue, Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(10.dp))
                    StatTile(strings.totalSeconds, "%,d".format(parts.totalSeconds), AppColors.Green)
                }
                Spacer(Modifier.height(14.dp))
            }

            // ---- Next birthday ----
            item {
                GlassCard(accent = AppColors.Pink) {
                    SectionHeader(Icons.Filled.Schedule, strings.nextBirthday, accent = AppColors.Pink)
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatTile(strings.daysToGo, parts.daysToNextBirthday.toString(), AppColors.Pink, Modifier.weight(1f))
                        StatTile(strings.turning, parts.nextBirthdayAge.toString(), AppColors.Gold, Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(14.dp))
            }

            // ---- Star sign ----
            item {
                GlassCard(accent = AppColors.Gold) {
                    SectionHeader(Icons.Filled.NightsStay, strings.starSign, zodiac.dateRange)
                    Spacer(Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = zodiac.imageUrl,
                            contentDescription = zodiac.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(94.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(AppColors.Surface2.copy(alpha = 0.5f))
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            GradientText(
                                text = "${zodiac.symbol}  ${zodiac.name}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${Zodiac.elementSymbol(zodiac.element)} ${z.element} • ${z.quality} • ${z.rulingPlanet}",
                                color = AppColors.Muted,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        z.description,
                        color = AppColors.GoldSoft,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(16.dp))
                    InfoRow(strings.element, z.element)
                    InfoRow(strings.quality, z.quality)
                    InfoRow(strings.rulingPlanet, z.rulingPlanet)
                    InfoRow(strings.luckyNumbers, zodiac.luckyNumbers)
                    InfoRow(strings.luckyColour, zodiac.luckyColor)
                    InfoRow(strings.bestMatch, zodiac.compatible)
                    Spacer(Modifier.height(12.dp))
                    Text(strings.strengths, color = AppColors.Green, style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(5.dp))
                    Text(z.strengths, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(10.dp))
                    Text(strings.watchOuts, color = AppColors.Danger, style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(5.dp))
                    Text(z.weaknesses, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        z.traits.take(3).forEach { Tag(it, AppColors.Gold) }
                    }
                }
                Spacer(Modifier.height(14.dp))
            }

            // ---- Exact date summary ----
            item {
                GlassCard(accent = AppColors.Gold) {
                    SectionHeader(
                        Icons.Filled.EventAvailable,
                        strings.exactDate,
                        Repo.fullDate(b.toLocalDate(), language)
                    )
                    Spacer(Modifier.height(12.dp))
                    when (val s = exactState) {
                        is LoadState.Loading -> LoadingBlock(strings.searchingWikidata)
                        is LoadState.Ready -> if (s.data.isEmpty()) {
                            EmptyBlock(strings.noNotable)
                        } else {
                            Text(
                                "${s.data.size} ${strings.sharing}",
                                color = AppColors.Muted,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        else -> EmptyBlock(strings.enterDobFirst)
                    }
                }
                Spacer(Modifier.height(14.dp))
            }

            if (exactState is LoadState.Ready) {
                val list = (exactState as LoadState.Ready<List<BirthEntry>>).data
                items(list) { entry ->
                    PersonCard(entry, AppColors.Gold, strings)
                    Spacer(Modifier.height(12.dp))
                }
            }

            // ---- Same day, any year ----
            item {
                GlassCard(accent = AppColors.Violet) {
                    SectionHeader(
                        Icons.Filled.Public,
                        strings.famousDay,
                        strings.everyYear,
                        AppColors.Violet
                    )
                    Spacer(Modifier.height(12.dp))
                    when (val s = dayState) {
                        is LoadState.Loading -> LoadingBlock(strings.collecting)
                        is LoadState.Ready -> Text(
                            "${s.data.size} ${strings.sharing}",
                            color = AppColors.Muted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        else -> EmptyBlock(strings.enterDobFirst)
                    }
                }
                Spacer(Modifier.height(14.dp))
            }

            if (dayState is LoadState.Ready) {
                val list = (dayState as LoadState.Ready<List<BirthEntry>>).data
                if (list.isEmpty()) {
                    item { EmptyBlock(strings.noNotable) }
                } else {
                    items(list) { entry ->
                        PersonCard(entry, AppColors.Violet, strings)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }

            if (adsEnabled) {
                item {
                    Spacer(Modifier.height(6.dp))
                    AdBannerSlot(adLabel)
                }
            }
        }
    }
}

@Composable
private fun TimerRow(items: List<Triple<String, Long, Color>>) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach { (label, value, color) ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha = 0.10f))
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "%02d".format(value),
                    style = MaterialTheme.typography.headlineMedium,
                    color = color,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(2.dp))
                Text(label, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun PersonCard(entry: BirthEntry, accent: Color, strings: Strings) {
    GlassCard(accent = accent, contentPadding = PaddingValues(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RemoteImage(
                url = entry.imageUrl,
                contentDescription = entry.personName,
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    entry.personName,
                    color = AppColors.Text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (entry.year > 0) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "${strings.bornYear} ${entry.year}",
                        color = accent,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                if (entry.description.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        entry.description,
                        color = AppColors.Muted,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3
                    )
                }
            }
        }
        if (entry.extract.isNotBlank() && entry.extract.length > entry.description.length + 10) {
            Spacer(Modifier.height(10.dp))
            Text(
                entry.extract,
                color = AppColors.Text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5
            )
        }
    }
}
