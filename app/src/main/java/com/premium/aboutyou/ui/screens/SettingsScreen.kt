package com.premium.aboutyou.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.premium.aboutyou.BuildConfig
import com.premium.aboutyou.MainViewModel
import com.premium.aboutyou.data.Language
import com.premium.aboutyou.data.Prefs
import com.premium.aboutyou.data.Strings
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors

@Composable
fun SettingsScreen(
    vm: MainViewModel,
    language: Language,
    onLanguageChange: (Language) -> Unit,
    adsEnabled: Boolean,
    onAdsChange: (Boolean) -> Unit,
    contentPadding: PaddingValues
) {
    val context = LocalContext.current
    val strings = vm.strings

    var reminder by remember { mutableStateOf(Prefs.birthdayReminder) }
    var daily by remember { mutableStateOf(Prefs.dailyInsight) }
    var showLanguages by remember { mutableStateOf(false) }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(18.dp))
            ScreenHeader(strings.tabSettings, accent = AppColors.Cyan)
            Spacer(Modifier.height(16.dp))

            // ---- Appearance / language ----
            GlassCard(accent = AppColors.Cyan) {
                SectionHeader(Icons.Filled.Palette, strings.setAppearance, accent = AppColors.Cyan)
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Language, contentDescription = null, tint = AppColors.Cyan)
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(strings.setLanguage, color = AppColors.Text, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${strings.setLanguageSub} • ${language.nativeName}",
                            color = AppColors.Muted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        if (showLanguages) "▲" else "▼",
                        color = AppColors.Cyan,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { showLanguages = !showLanguages }
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        language.displayName,
                        color = AppColors.Gold,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (showLanguages) {
                    Spacer(Modifier.height(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Language.entries.forEach { lang ->
                            val selected = lang == language
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (selected) AppColors.Cyan.copy(alpha = 0.16f)
                                        else AppColors.Surface2.copy(alpha = 0.4f)
                                    )
                                    .border(
                                        1.dp,
                                        if (selected) AppColors.Cyan.copy(alpha = 0.55f) else AppColors.Stroke,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onLanguageChange(lang) }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    lang.displayName,
                                    color = if (selected) AppColors.Cyan else AppColors.Text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                if (selected) {
                                    Text("✓", color = AppColors.Cyan, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---- Notifications ----
            GlassCard(accent = AppColors.Violet) {
                SectionHeader(Icons.Filled.Notifications, strings.setNotifications, accent = AppColors.Violet)
                Spacer(Modifier.height(6.dp))
                SettingToggle(
                    strings.setBirthdayReminder,
                    strings.setBirthdayReminderSub,
                    reminder,
                    AppColors.Violet
                ) { reminder = it; Prefs.birthdayReminder = it }
                SettingToggle(
                    strings.setDailyInsight,
                    strings.setDailyInsightSub,
                    daily,
                    AppColors.Violet
                ) { daily = it; Prefs.dailyInsight = it }
            }

            Spacer(Modifier.height(14.dp))

            // ---- Monetisation ----
            GlassCard(accent = AppColors.Gold) {
                SectionHeader(Icons.Filled.WorkspacePremium, strings.setMonetisation)
                Spacer(Modifier.height(6.dp))
                SettingToggle(
                    strings.setAds,
                    strings.setAdsSub,
                    adsEnabled,
                    AppColors.Gold
                ) { onAdsChange(it) }
                Spacer(Modifier.height(6.dp))
                Text(
                    strings.setAdsOffNote,
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (adsEnabled) {
                    Spacer(Modifier.height(12.dp))
                    AdBannerSlot(strings.adPlaceholder)
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---- Content sources ----
            GlassCard(accent = AppColors.Pink) {
                SectionHeader(Icons.Filled.Public, strings.setContent, accent = AppColors.Pink)
                Spacer(Modifier.height(12.dp))
                InfoRow(strings.setSources, strings.setSourcesSub)
                InfoRow(strings.setVersion, BuildConfig.VERSION_NAME)
            }

            Spacer(Modifier.height(14.dp))

            // ---- Print & share ----
            GlassCard(accent = AppColors.Cyan) {
                SectionHeader(Icons.Filled.Share, strings.setPrint, strings.setPrintSub, AppColors.Cyan)
                Spacer(Modifier.height(14.dp))
                ActionRow(
                    Icons.Filled.Share,
                    strings.setShare,
                    AppColors.Cyan
                ) {
                    val text = vm.buildReportText()
                    val send = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    context.startActivity(Intent.createChooser(send, strings.shareVia))
                }
                Spacer(Modifier.height(10.dp))
                ActionRow(
                    Icons.Filled.ContentCopy,
                    strings.setCopy,
                    AppColors.Gold
                ) {
                    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText(strings.heroTitle, vm.buildReportText()))
                    Toast.makeText(context, strings.setCopy, Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---- About / clear ----
            GlassCard {
                SectionHeader(Icons.Filled.Info, strings.setAbout)
                Spacer(Modifier.height(12.dp))
                Text(strings.poweredBy, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                ActionRow(
                    Icons.Filled.DeleteOutline,
                    strings.setClear,
                    AppColors.Danger
                ) {
                    Prefs.clear()
                    Toast.makeText(context, strings.setCleared, Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun SettingToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    accent: Color,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = AppColors.Text, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppColors.Bg,
                checkedTrackColor = accent,
                uncheckedThumbColor = AppColors.Muted,
                uncheckedTrackColor = AppColors.Surface2
            )
        )
    }
}

@Composable
private fun ActionRow(icon: ImageVector, label: String, accent: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(accent.copy(alpha = 0.10f))
            .border(1.dp, accent.copy(alpha = 0.30f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(
            label,
            color = AppColors.Text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}
