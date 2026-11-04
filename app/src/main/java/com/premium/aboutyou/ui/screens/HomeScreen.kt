package com.premium.aboutyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premium.aboutyou.data.Dict
import com.premium.aboutyou.data.Language
import com.premium.aboutyou.data.Strings
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors

/**
 * Home is the entry point: the user writes a name, types a date of birth with
 * the keyboard and picks the language the results should come back in, then
 * taps Analyze.
 */
@Composable
fun HomeScreen(
    strings: Strings,
    language: Language,
    name: String,
    onNameChange: (String) -> Unit,
    day: String,
    month: String,
    year: String,
    hour: String,
    minute: String,
    onDay: (String) -> Unit,
    onMonth: (String) -> Unit,
    onYear: (String) -> Unit,
    onHour: (String) -> Unit,
    onMinute: (String) -> Unit,
    onLanguageChange: (Language) -> Unit,
    onAnalyze: () -> Unit,
    adsEnabled: Boolean,
    adLabel: String,
    contentPadding: PaddingValues
) {
    val canAnalyze = name.isNotBlank() && day.isNotBlank() && month.isNotBlank() && year.isNotBlank()

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(18.dp))
            Hero(strings)

            Spacer(Modifier.height(22.dp))

            // ---- Name ----
            GlassCard(accent = AppColors.Gold) {
                SectionHeader(Icons.Filled.Badge, strings.nameLabel, strings.stepName)
                Spacer(Modifier.height(14.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text(strings.nameLabel) },
                    placeholder = { Text(strings.nameHint) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = AppColors.Gold)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = fieldColors(AppColors.Gold)
                )
            }

            Spacer(Modifier.height(16.dp))

            // ---- Date of birth, typed ----
            GlassCard(accent = AppColors.Violet) {
                SectionHeader(Icons.Filled.Cake, strings.dobLabel, strings.stepDate)
                Spacer(Modifier.height(6.dp))
                Text(
                    strings.dobHint,
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    NumberField(
                        value = day,
                        onValueChange = onDay,
                        label = strings.fDay,
                        placeholder = "DD",
                        max = 2,
                        modifier = Modifier.weight(1f),
                        accent = AppColors.Violet
                    )
                    NumberField(
                        value = month,
                        onValueChange = onMonth,
                        label = strings.fMonth,
                        placeholder = "MM",
                        max = 2,
                        modifier = Modifier.weight(1f),
                        accent = AppColors.Violet
                    )
                    NumberField(
                        value = year,
                        onValueChange = onYear,
                        label = strings.fYear,
                        placeholder = "YYYY",
                        max = 4,
                        modifier = Modifier.weight(1.3f),
                        accent = AppColors.Violet
                    )
                }
                Spacer(Modifier.height(14.dp))
                Text(
                    strings.optionalTime,
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    NumberField(
                        value = hour,
                        onValueChange = onHour,
                        label = strings.fHour,
                        placeholder = "HH",
                        max = 2,
                        modifier = Modifier.weight(1f),
                        accent = AppColors.Cyan
                    )
                    NumberField(
                        value = minute,
                        onValueChange = onMinute,
                        label = strings.fMinute,
                        placeholder = "MM",
                        max = 2,
                        modifier = Modifier.weight(1f),
                        accent = AppColors.Cyan
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---- Language ----
            GlassCard(accent = AppColors.Cyan) {
                SectionHeader(Icons.Filled.Language, strings.languageLabel, strings.stepLanguage)
                Spacer(Modifier.height(14.dp))
                LanguageChips(language, onLanguageChange)
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onAnalyze,
                enabled = canAnalyze,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Gold,
                    contentColor = AppColors.Bg,
                    disabledContainerColor = AppColors.Surface2,
                    disabledContentColor = AppColors.Muted
                )
            ) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = null)
                Spacer(Modifier.width(9.dp))
                Text(strings.analyze, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            if (!canAnalyze) {
                Spacer(Modifier.height(8.dp))
                Text(
                    strings.analyzeHint,
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (adsEnabled) {
                Spacer(Modifier.height(18.dp))
                AdBannerSlot(adLabel)
            }

            Spacer(Modifier.height(20.dp))
            Text(
                strings.poweredBy,
                color = AppColors.Muted,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun Hero(strings: Strings) {
    Column {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(AppColors.Gold.copy(alpha = 0.12f))
                .border(1.dp, AppColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                strings.heroBadge,
                color = AppColors.Gold,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(14.dp))
        GradientText(
            text = strings.heroTitle,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            strings.heroSubtitle,
            color = AppColors.Muted,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        StepBadge(1, strings.stepName, AppColors.Gold)
        Spacer(Modifier.height(8.dp))
        StepBadge(2, strings.stepDate, AppColors.Violet)
        Spacer(Modifier.height(8.dp))
        StepBadge(3, strings.stepLanguage, AppColors.Cyan)
    }
}

@Composable
private fun LanguageChips(current: Language, onPick: (Language) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Language.entries.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { lang ->
                    val selected = lang == current
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selected) AppColors.Cyan.copy(alpha = 0.18f)
                                else AppColors.Surface2.copy(alpha = 0.5f)
                            )
                            .border(
                                1.dp,
                                if (selected) AppColors.Cyan.copy(alpha = 0.65f) else AppColors.Stroke,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onPick(lang) }
                            .padding(horizontal = 12.dp, vertical = 11.dp)
                    ) {
                        Text(
                            lang.nativeName,
                            color = if (selected) AppColors.Cyan else AppColors.Text,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                    }
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun NumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    max: Int,
    modifier: Modifier = Modifier,
    accent: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input -> onValueChange(input.filter { it.isDigit() }.take(max)) },
        label = { Text(label, maxLines = 1) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = fieldColors(accent)
    )
}

@Composable
private fun fieldColors(accent: Color) = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = accent,
    unfocusedBorderColor = AppColors.Stroke,
    focusedLabelColor = accent,
    unfocusedLabelColor = AppColors.Muted,
    cursorColor = accent,
    focusedTextColor = AppColors.Text,
    unfocusedTextColor = AppColors.Text,
    focusedContainerColor = AppColors.Surface2.copy(alpha = 0.35f),
    unfocusedContainerColor = Color.Transparent
)
