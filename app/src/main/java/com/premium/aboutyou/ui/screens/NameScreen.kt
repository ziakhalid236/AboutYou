package com.premium.aboutyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.premium.aboutyou.LoadState
import com.premium.aboutyou.MainViewModel
import com.premium.aboutyou.data.Dict
import com.premium.aboutyou.data.Language
import com.premium.aboutyou.data.LocalNameData
import com.premium.aboutyou.data.NameMeaning
import com.premium.aboutyou.data.NameReport
import com.premium.aboutyou.data.Strings
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors

@Composable
fun NameScreen(vm: MainViewModel, contentPadding: PaddingValues) {
    val state by vm.nameState.collectAsState()
    val language by vm.language.collectAsState()
    val strings = vm.strings

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(18.dp))
            ScreenHeader(
                title = strings.nameTitle,
                subtitle = vm.lastSearchedName.ifBlank { null },
                accent = AppColors.Gold
            )
            Spacer(Modifier.height(16.dp))

            when (val s = state) {
                is LoadState.Loading -> LoadingBlock(strings.searchingName)
                is LoadState.Idle -> EmptyBlock(strings.nameEmpty)
                is LoadState.Failed -> EmptyBlock(s.message)
                is LoadState.Ready -> NameContent(s.data, language, strings)
            }
            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun NameContent(report: NameReport, language: Language, strings: Strings) {
    val raw = report.local ?: LocalNameData.generated(
        report.query,
        genderHint = report.stats?.genderGuess,
        originHint = report.stats?.countries?.firstOrNull()?.first
    )
    val local = raw.localized(language)

    GlassCard(accent = AppColors.Gold) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppColors.Gold.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    report.query.trim().firstOrNull()?.uppercase() ?: "?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = AppColors.Gold,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                GradientText(text = report.query.replaceFirstChar { it.uppercase() })
                Text(
                    "${local.gender} • ${local.origin}",
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(strings.meaning, color = AppColors.Gold, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(5.dp))
        Text(local.meaning, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
        if (local.secondaryMeaning.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(local.secondaryMeaning, color = AppColors.GoldSoft, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tag(local.culture, AppColors.Violet)
            Tag("${strings.luckyNumber}: ${local.luckyNumber}", AppColors.Cyan)
        }
    }

    Spacer(Modifier.height(14.dp))

    GlassCard {
        SectionHeader(Icons.Filled.Badge, strings.nameProfile)
        Spacer(Modifier.height(10.dp))
        InfoRow(strings.origin, local.origin)
        InfoRow(strings.culture, local.culture)
        InfoRow(strings.gender, local.gender)
        InfoRow(strings.luckyNumber, local.luckyNumber)
        InfoRow(strings.luckyColour, local.luckyColor)
        report.stats?.genderProbability?.let { p ->
            InfoRow(strings.confidence, "${(p * 100).toInt()}%")
        }
    }

    Spacer(Modifier.height(14.dp))
    GlassCard(accent = AppColors.Cyan) {
        SectionHeader(Icons.Filled.Star, strings.traits, accent = AppColors.Cyan)
        Spacer(Modifier.height(12.dp))
        FlowTags(local.traits)
    }

    report.stats?.let { st ->
        Spacer(Modifier.height(14.dp))
        GlassCard(accent = AppColors.Violet) {
            SectionHeader(Icons.Filled.Public, strings.liveStats, "genderize.io • nationalize.io", AppColors.Violet)
            Spacer(Modifier.height(12.dp))
            st.genderGuess?.let {
                InfoRow(strings.mostlyUsed, Dict.word(it, language))
            }
            if (st.countries.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(strings.mostCommonIn, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                st.countries.forEach { (country, p) ->
                    Column(modifier = Modifier.padding(vertical = 5.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                country,
                                color = AppColors.Text,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "${(p * 100).toInt()}%",
                                color = AppColors.Gold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(Modifier.height(5.dp))
                        ProgressBar(p.toFloat(), AppColors.Violet)
                    }
                }
            }
        }
    }

    report.summary?.let { sum ->
        Spacer(Modifier.height(14.dp))
        GlassCard(accent = AppColors.Gold) {
            SectionHeader(Icons.Filled.School, strings.encyclopaedia, sum.title)
            Spacer(Modifier.height(12.dp))
            if (!sum.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = sum.imageUrl,
                    contentDescription = sum.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(Modifier.height(12.dp))
            }
            Text(sum.extract, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
        }
    }

    Spacer(Modifier.height(14.dp))
    GlassCard(accent = AppColors.Pink) {
        SectionHeader(Icons.Filled.Celebration, strings.famousNames, accent = AppColors.Pink)
        Spacer(Modifier.height(12.dp))
        if (report.notable.isEmpty()) {
            EmptyBlock(strings.noneFound)
        } else {
            report.notable.forEachIndexed { i, p ->
                NotableRow(p)
                if (i != report.notable.lastIndex) Spacer(Modifier.height(12.dp))
            }
        }
    }
}

/** Applies the chosen language to a curated entry. */
private fun NameMeaning.localized(lang: Language) = LocalizedName(
    meaning = meaning,
    secondaryMeaning = if (lang == Language.URDU) urduMeaning else "",
    origin = origin,
    culture = Dict.word(culture, lang),
    gender = Dict.word(gender, lang),
    luckyNumber = luckyNumber,
    luckyColor = luckyColor,
    traits = traits.map { Dict.word(it, lang) }
)

private data class LocalizedName(
    val meaning: String,
    val secondaryMeaning: String,
    val origin: String,
    val culture: String,
    val gender: String,
    val luckyNumber: String,
    val luckyColor: String,
    val traits: List<String>
)

@Composable
private fun NotableRow(p: com.premium.aboutyou.data.NotablePerson) {
    Row {
        RemoteImage(
            url = p.imageUrl,
            contentDescription = p.title,
            modifier = Modifier.size(62.dp),
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(p.title, color = AppColors.Text, style = MaterialTheme.typography.titleMedium)
            if (p.extract.isNotBlank()) {
                Spacer(Modifier.height(3.dp))
                Text(
                    p.extract,
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4
                )
            }
        }
    }
}

@Composable
private fun FlowTags(tags: List<String>) {
    tags.chunked(2).forEach { row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            row.forEach { t -> Tag(t, AppColors.Cyan) }
        }
    }
}
