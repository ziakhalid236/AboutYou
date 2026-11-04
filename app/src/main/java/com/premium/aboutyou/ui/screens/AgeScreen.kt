package com.premium.aboutyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.premium.aboutyou.data.Zodiac
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors
import com.premium.aboutyou.util.AgeCalc
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AgeScreen(birth: LocalDateTime, onBack: () -> Unit) {
    var now by remember { mutableStateOf(LocalDateTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = LocalDateTime.now()
            delay(1000)
        }
    }

    val parts = remember(birth, now) { AgeCalc.compute(birth, now) }
    val zodiac = remember(birth) { Zodiac.forDate(birth.toLocalDate()) }
    val fmt = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH)
    val timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            ScreenHeader(
                title = "Your Time Alive",
                subtitle = "Live, updated every second",
                onBack = onBack
            )
            Spacer(Modifier.height(16.dp))

            // ---- Birth stamp ----
            GlassCard(accent = AppColors.Gold) {
                SectionHeader(Icons.Filled.Cake, "Born On")
                Spacer(Modifier.height(10.dp))
                GradientText(
                    text = birth.format(fmt),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "at ${birth.format(timeFmt)} • ${parts.weekdayBorn}",
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(14.dp))

            // ---- Live premium timer ----
            GlassCard(accent = AppColors.Cyan) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SectionHeader(Icons.Filled.Bolt, "Live Counter", "years • months • days • hours • minutes • seconds")
                }
                Spacer(Modifier.height(16.dp))
                TimerRow(
                    listOf(
                        Triple("Years", parts.years, AppColors.Gold),
                        Triple("Months", parts.months, AppColors.Violet),
                        Triple("Days", parts.days, AppColors.Cyan)
                    )
                )
                Spacer(Modifier.height(10.dp))
                TimerRow(
                    listOf(
                        Triple("Hours", parts.hours, AppColors.Pink),
                        Triple("Minutes", parts.minutes, AppColors.Blue),
                        Triple("Seconds", parts.seconds, AppColors.Green)
                    )
                )
            }

            Spacer(Modifier.height(14.dp))

            // ---- Totals ----
            GlassCard(accent = AppColors.Violet) {
                SectionHeader(Icons.Filled.Summarize, "Total Time", "everything added up")
                Spacer(Modifier.height(14.dp))
                StatTile("Total Months", "%,d".format(parts.totalMonths), AppColors.Violet)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatTile("Total Weeks", "%,d".format(parts.totalWeeks), AppColors.Cyan, Modifier.weight(1f))
                    StatTile("Total Days", "%,d".format(parts.totalDays), AppColors.Gold, Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatTile("Total Hours", "%,d".format(parts.totalHours), AppColors.Pink, Modifier.weight(1f))
                    StatTile("Total Minutes", "%,d".format(parts.totalMinutes), AppColors.Blue, Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
                StatTile("Total Seconds", "%,d".format(parts.totalSeconds), AppColors.Green)
            }

            Spacer(Modifier.height(14.dp))

            // ---- Birthday countdown ----
            GlassCard(accent = AppColors.Pink) {
                SectionHeader(Icons.Filled.Schedule, "Next Birthday")
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatTile("Days To Go", parts.daysToNextBirthday.toString(), AppColors.Pink, Modifier.weight(1f))
                    StatTile("Turning", parts.nextBirthdayAge.toString(), AppColors.Gold, Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---- Star sign ----
            GlassCard(accent = AppColors.Gold) {
                SectionHeader(Icons.Filled.NightsStay, "Your Star Sign", zodiac.dateRange)
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = zodiac.imageUrl,
                        contentDescription = zodiac.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        GradientText(text = "${zodiac.symbol}  ${zodiac.name}", style = MaterialTheme.typography.headlineMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "${Zodiac.elementSymbol(zodiac.element)} ${zodiac.element} • ${zodiac.quality} • ${zodiac.rulingPlanet}",
                            color = AppColors.Muted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(zodiac.urduDescription, color = AppColors.GoldSoft, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Start)
                Spacer(Modifier.height(16.dp))
                InfoRow("Element", zodiac.element)
                InfoRow("Quality", zodiac.quality)
                InfoRow("Ruling Planet", zodiac.rulingPlanet)
                InfoRow("Lucky Numbers", zodiac.luckyNumbers)
                InfoRow("Lucky Colour", zodiac.luckyColor)
                InfoRow("Best Match", zodiac.compatible)
                Spacer(Modifier.height(12.dp))
                Text("Strengths", color = AppColors.Green, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(4.dp))
                Text(zodiac.strengths, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(10.dp))
                Text("Watch-outs", color = AppColors.Danger, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(4.dp))
                Text(zodiac.weaknesses, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    zodiac.traits.take(3).forEach { Tag(it, AppColors.Gold) }
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun TimerRow(items: List<Triple<String, Long, androidx.compose.ui.graphics.Color>>) {
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
