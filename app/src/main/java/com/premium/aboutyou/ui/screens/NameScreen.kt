package com.premium.aboutyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.premium.aboutyou.data.LocalNameData
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors

@Composable
fun NameScreen(vm: MainViewModel, onBack: () -> Unit) {
    val state by vm.nameState.collectAsState()

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            ScreenHeader(
                title = "Name Insights",
                subtitle = vm.lastSearchedName.ifBlank { null },
                onBack = onBack
            )
            Spacer(Modifier.height(16.dp))

            when (val s = state) {
                is LoadState.Loading -> LoadingBlock("Searching the web for this name…")
                is LoadState.Idle -> EmptyBlock("Search a name from the home screen to see its meaning.")
                is LoadState.Failed -> EmptyBlock(s.message)
                is LoadState.Ready -> NameContent(s.data)
            }
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun NameContent(report: com.premium.aboutyou.data.NameReport) {
    val local = report.local ?: LocalNameData.generated(
        report.query,
        genderHint = report.stats?.genderGuess,
        originHint = report.stats?.countries?.firstOrNull()?.first
    )

    // ---- Hero: meaning ----
    GlassCard(accent = AppColors.Gold) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
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
            Column {
                GradientText(text = report.query.replaceFirstChar { it.uppercase() })
                Text("${local.gender} • ${local.origin}", color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Meaning", color = AppColors.Gold, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(4.dp))
        Text(local.meaning, color = AppColors.Text, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(12.dp))
        Text(local.urduMeaning, color = AppColors.GoldSoft, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tag(local.culture, AppColors.Violet)
            Tag("Lucky ${local.luckyNumber}", AppColors.Cyan)
        }
    }

    Spacer(Modifier.height(14.dp))

    // ---- Quick facts ----
    GlassCard {
        SectionHeader(Icons.Filled.Badge, "Name Profile")
        Spacer(Modifier.height(10.dp))
        InfoRow("Origin", local.origin)
        InfoRow("Culture", local.culture)
        InfoRow("Gender", local.gender)
        InfoRow("Lucky Number", local.luckyNumber)
        InfoRow("Lucky Colour", local.luckyColor)
        if (report.stats?.genderProbability != null) {
            val confidence = (report.stats.genderProbability * 100).toInt()
            InfoRow(
                "Data Confidence",
                "$confidence% match across millions of records"
            )
        }
    }

    // ---- Traits ----
    Spacer(Modifier.height(14.dp))
    GlassCard(accent = AppColors.Cyan) {
        SectionHeader(Icons.Filled.Star, "Personality Traits")
        Spacer(Modifier.height(12.dp))
        FlowTags(local.traits)
    }

    // ---- Live statistics ----
    report.stats?.let { st ->
        Spacer(Modifier.height(14.dp))
        GlassCard(accent = AppColors.Violet) {
            SectionHeader(Icons.Filled.Public, "Live Name Statistics", "genderize.io and nationalize.io")
            Spacer(Modifier.height(12.dp))
            st.genderGuess?.let {
                InfoRow("Mostly used as", "$it name")
            }
            if (st.countries.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text("Most common in", color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                st.countries.forEach { (country, p) ->
                    Column(modifier = Modifier.padding(vertical = 5.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(country, color = AppColors.Text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                            Text("${(p * 100).toInt()}%", color = AppColors.Gold, style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(Modifier.height(5.dp))
                        ProgressBar(p.toFloat(), AppColors.Violet)
                    }
                }
            }
        }
    }

    // ---- Wikipedia summary ----
    report.summary?.let { sum ->
        Spacer(Modifier.height(14.dp))
        GlassCard(accent = AppColors.Gold) {
            SectionHeader(Icons.Filled.School, "From the Encyclopaedia", sum.title)
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

    // ---- Famous people with this name ----
    Spacer(Modifier.height(14.dp))
    GlassCard(accent = AppColors.Pink) {
        SectionHeader(Icons.Filled.Celebration, "Famous People With This Name", "from Wikipedia")
        Spacer(Modifier.height(12.dp))
        if (report.notable.isEmpty()) {
            EmptyBlock("No well-known people were found for this exact name.")
        } else {
            report.notable.forEachIndexed { i, p ->
                NotableRow(p)
                if (i != report.notable.lastIndex) Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun NotableRow(p: com.premium.aboutyou.data.NotablePerson) {
    Row {
        RemoteImage(
            url = p.imageUrl,
            contentDescription = p.title,
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(p.title, color = AppColors.Text, style = MaterialTheme.typography.titleMedium)
            if (p.extract.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
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
    // Simple wrapped rows: two per line keeps it tidy on phones.
    tags.chunked(2).forEach { row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            row.forEach { t ->
                Tag(t, AppColors.Cyan)
            }
        }
    }
}
