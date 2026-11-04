package com.premium.aboutyou.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.premium.aboutyou.LoadState
import com.premium.aboutyou.MainViewModel
import com.premium.aboutyou.data.BirthEntry
import com.premium.aboutyou.net.Repo
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors

@Composable
fun BirthdayScreen(vm: MainViewModel, onBack: () -> Unit) {
    val date = vm.lastBirthDate
    val dayState by vm.dayBirths.collectAsState()
    val exactState by vm.exactBirths.collectAsState()

    AppBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp)
        ) {
            item {
                ScreenHeader(
                    title = "Born On Your Day",
                    subtitle = date?.let { "${Repo.monthName(it.monthValue)} ${it.dayOfMonth}" },
                    onBack = onBack
                )
                Spacer(Modifier.height(16.dp))
            }

            // ---- Exact date (day + month + year) ----
            item {
                GlassCard(accent = AppColors.Gold) {
                    SectionHeader(
                        Icons.Filled.EventAvailable,
                        "On Your Exact Date",
                        date?.let { "${it.dayOfMonth} ${Repo.monthName(it.monthValue)} ${it.year}" }
                    )
                    Spacer(Modifier.height(12.dp))
                    when (val s = exactState) {
                        is LoadState.Loading -> LoadingBlock("Searching Wikidata for this exact day…")
                        is LoadState.Ready -> if (s.data.isEmpty()) {
                            EmptyBlock("No notable births were found for this exact date. The list below shows everyone born on this day in any year.")
                        } else {
                            Text(
                                "${s.data.size} notable people share your exact birthday",
                                color = AppColors.Muted,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        else -> EmptyBlock("Open from the date picker to load this list.")
                    }
                }
                Spacer(Modifier.height(14.dp))
            }

            if (exactState is LoadState.Ready) {
                val list = (exactState as LoadState.Ready<List<BirthEntry>>).data
                items(list) { entry ->
                    PersonCard(entry, AppColors.Gold)
                    Spacer(Modifier.height(12.dp))
                }
            }

            // ---- Same day, any year ----
            item {
                GlassCard(accent = AppColors.Violet) {
                    SectionHeader(
                        Icons.Filled.Public,
                        "Famous Birthdays On This Day",
                        "Every year • from Wikipedia's world feed"
                    )
                    Spacer(Modifier.height(12.dp))
                    when (val s = dayState) {
                        is LoadState.Loading -> LoadingBlock("Collecting birthdays from around the world…")
                        is LoadState.Ready -> Text(
                            "${s.data.size} famous people were born on this day across history",
                            color = AppColors.Muted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        else -> EmptyBlock("Open from the date picker to load this list.")
                    }
                }
                Spacer(Modifier.height(14.dp))
            }

            if (dayState is LoadState.Ready) {
                val list = (dayState as LoadState.Ready<List<BirthEntry>>).data
                if (list.isEmpty()) {
                    item { EmptyBlock("No birthday records were returned for this day.") }
                } else {
                    items(list) { entry ->
                        PersonCard(entry, AppColors.Violet)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PersonCard(entry: BirthEntry, accent: androidx.compose.ui.graphics.Color) {
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
                        "Born ${entry.year}",
                        color = accent,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    entry.description,
                    color = AppColors.Muted,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
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
