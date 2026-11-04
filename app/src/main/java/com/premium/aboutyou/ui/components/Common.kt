package com.premium.aboutyou.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.premium.aboutyou.ui.theme.AppColors

@Composable
fun AppBackground(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgGradient)
    ) {
        // Soft ambient glows for a premium, layered feel.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(AppColors.Violet.copy(alpha = 0.28f), Color.Transparent),
                        radius = 900f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.radialGradient(
                        colors = listOf(AppColors.Gold.copy(alpha = 0.16f), Color.Transparent),
                        radius = 800f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            content = content
        )
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    accent: Color? = null,
    contentPadding: PaddingValues = PaddingValues(18.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(22.dp)
    val borderBrush = if (accent != null) {
        Brush.linearGradient(
            listOf(accent.copy(alpha = 0.55f), accent.copy(alpha = 0.10f), Color.White.copy(alpha = 0.06f))
        )
    } else {
        Brush.linearGradient(
            listOf(Color.White.copy(alpha = 0.16f), Color.White.copy(alpha = 0.04f))
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    listOf(AppColors.Surface.copy(alpha = 0.92f), AppColors.Surface2.copy(alpha = 0.72f))
                )
            )
            .border(1.dp, borderBrush, shape)
            .padding(contentPadding),
        content = content
    )
}

@Composable
fun GradientText(
    text: String,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.headlineMedium,
    brush: Brush = AppColors.GoldGradient,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style.copy(brush = brush),
        textAlign = textAlign
    )
}

@Composable
fun SectionHeader(icon: ImageVector, title: String, subtitle: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(AppColors.Gold.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = AppColors.Gold, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(10.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium, color = AppColors.Text)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = AppColors.Muted)
            }
        }
    }
}

@Composable
fun Tag(text: String, color: Color = AppColors.Gold) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.14f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyMedium, color = color, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StatTile(label: String, value: String, accent: Color = AppColors.Gold, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface2.copy(alpha = 0.7f))
            .border(1.dp, accent.copy(alpha = 0.22f), RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            style = MaterialTheme.typography.titleLarge,
            color = accent,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Spacer(Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = AppColors.Muted, textAlign = TextAlign.Center)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = AppColors.Muted, modifier = Modifier.width(112.dp))
        Text(value, style = MaterialTheme.typography.bodyLarge, color = AppColors.Text, modifier = Modifier.weight(1f))
    }
}

@Composable
fun LoadingBlock(text: String = "Fetching from the web…") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = AppColors.Gold, strokeWidth = 3.dp)
        Spacer(Modifier.height(12.dp))
        Text(text, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "alpha"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .alpha(alpha)
            .background(AppColors.Surface2)
    )
}

@Composable
fun EmptyBlock(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface2.copy(alpha = 0.6f))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
    }
}

@Composable
fun RemoteImage(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp)
) {
    if (url.isNullOrBlank()) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(Brush.linearGradient(listOf(AppColors.Surface2, AppColors.Surface))),
            contentAlignment = Alignment.Center
        ) {
            Text("✦", color = AppColors.Gold.copy(alpha = 0.5f))
        }
    } else {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(shape)
        )
    }
}

@Composable
fun ScreenHeader(title: String, subtitle: String? = null, onBack: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AppColors.Text)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            GradientText(text = title, style = MaterialTheme.typography.headlineMedium)
            if (subtitle != null) {
                Text(subtitle, color = AppColors.Muted, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun Divider15() {
    Spacer(Modifier.height(14.dp))
}

@Composable
fun HeroIcon(icon: ImageVector, accent: Color = AppColors.Gold, size: Int = 46) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    listOf(accent.copy(alpha = 0.28f), accent.copy(alpha = 0.08f))
                )
            )
            .border(1.dp, accent.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size((size / 2).dp))
    }
}

@Composable
fun EmojiBadge(emoji: String, modifier: Modifier = Modifier, size: Int = 44) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(AppColors.Surface2.copy(alpha = 0.8f))
            .border(1.dp, AppColors.Gold.copy(alpha = 0.3f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(emoji, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun ProgressBar(fraction: Float, color: Color = AppColors.Gold) {
    val f = fraction.coerceIn(0f, 1f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(50))
            .background(AppColors.Surface2)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(Brush.horizontalGradient(listOf(color.copy(alpha = 0.7f), color)))
        )
    }
}

@Suppress("unused")
@Composable
private fun UnusedImage(image: ImageVector) {
    Image(imageVector = image, contentDescription = null)
}
