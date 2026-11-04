package com.premium.aboutyou.ui.screens

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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.premium.aboutyou.ui.components.*
import com.premium.aboutyou.ui.theme.AppColors

@Composable
fun HomeScreen(
    onSearchName: (String) -> Unit,
    onOpenDatePicker: () -> Unit,
    selectedDateLabel: String?
) {
    var name by remember { mutableStateOf("") }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            Text("✦", style = MaterialTheme.typography.displayLarge, color = AppColors.Gold)
            GradientText(
                text = "ABOUT YOU",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Discover the story behind your name, your exact age down to the second, " +
                    "your star sign and everyone who shares your birthday.",
                color = AppColors.Muted,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(26.dp))

            // ---- Name card ----
            GlassCard(accent = AppColors.Gold) {
                SectionHeader(Icons.Filled.Badge, "Your Name", "Meaning, origin and famous people")
                Spacer(Modifier.height(14.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Enter your name") },
                    placeholder = { Text("e.g. Muhammad Ali") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = AppColors.Gold) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { if (name.isNotBlank()) onSearchName(name) }),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.Gold,
                        unfocusedBorderColor = AppColors.Stroke,
                        focusedLabelColor = AppColors.Gold,
                        cursorColor = AppColors.Gold,
                        focusedTextColor = AppColors.Text,
                        unfocusedTextColor = AppColors.Text
                    )
                )
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = { if (name.isNotBlank()) onSearchName(name) },
                    enabled = name.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Gold,
                        contentColor = AppColors.Bg,
                        disabledContainerColor = AppColors.Surface2,
                        disabledContentColor = AppColors.Muted
                    )
                ) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Reveal My Name", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(18.dp))

            // ---- Date of birth card ----
            GlassCard(accent = AppColors.Violet) {
                SectionHeader(Icons.Filled.Cake, "Date of Birth", "Live age, star sign and shared birthdays")
                Spacer(Modifier.height(14.dp))
                if (selectedDateLabel != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Tag(selectedDateLabel, AppColors.Violet)
                    }
                    Spacer(Modifier.height(14.dp))
                }
                Button(
                    onClick = onOpenDatePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Violet,
                        contentColor = AppColors.Text
                    )
                ) {
                    Icon(Icons.Filled.CalendarMonth, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (selectedDateLabel == null) "Choose Date of Birth" else "Change Date",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(22.dp))
            Text(
                "Live data from Wikipedia, Wikimedia and Wikidata — searched across the whole internet, " +
                    "not limited to one platform.",
                color = AppColors.Muted,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}
