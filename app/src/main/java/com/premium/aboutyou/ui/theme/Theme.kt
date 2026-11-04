package com.premium.aboutyou.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppColors {
    val Bg = Color(0xFF0B0B12)
    val Bg2 = Color(0xFF101024)
    val Surface = Color(0xFF16162A)
    val Surface2 = Color(0xFF1E1E34)
    val Gold = Color(0xFFE9C46A)
    val GoldSoft = Color(0xFFF7E8B8)
    val Violet = Color(0xFF8B7CF6)
    val Cyan = Color(0xFF4FD1C5)
    val Pink = Color(0xFFF472B6)
    val Blue = Color(0xFF60A5FA)
    val Text = Color(0xFFF3F3F8)
    val Muted = Color(0xFF9AA0B4)
    val Stroke = Color(0x22FFFFFF)
    val Danger = Color(0xFFFF6B6B)
    val Green = Color(0xFF34D399)

    val BgGradient = Brush.verticalGradient(
        listOf(Color(0xFF0B0B12), Color(0xFF12122A), Color(0xFF0B0B12))
    )
    val GoldGradient = Brush.horizontalGradient(
        listOf(Color(0xFFE9C46A), Color(0xFFF7E8B8), Color(0xFFE9C46A))
    )
}

private val scheme = darkColorScheme(
    primary = AppColors.Gold,
    onPrimary = Color(0xFF1A1400),
    secondary = AppColors.Violet,
    onSecondary = Color.White,
    tertiary = AppColors.Cyan,
    background = AppColors.Bg,
    onBackground = AppColors.Text,
    surface = AppColors.Surface,
    onSurface = AppColors.Text,
    surfaceVariant = AppColors.Surface2,
    onSurfaceVariant = AppColors.Muted,
    outline = AppColors.Stroke,
    error = AppColors.Danger
)

private val typography = Typography(
    displayLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 44.sp, letterSpacing = (-1).sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 26.sp),
    titleLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 17.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 21.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, letterSpacing = 0.3.sp)
)

@Composable
fun AboutYouTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = scheme, typography = typography, content = content)
}
