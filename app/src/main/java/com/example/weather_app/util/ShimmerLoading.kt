package com.example.weather_app.util

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.shimmer(
    durationMillis: Int = 1200,
): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer_transition")

    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f, // distance the gradient will move
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_anim",
    )

    return drawBehind {
        val gradientWidth = 200f
        val brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.3f),
                Color.LightGray.copy(alpha = 0.9f),
                Color.LightGray.copy(alpha = 0.3f)
            ),
            start = Offset(translateAnim - gradientWidth, 0f),
            end = Offset(translateAnim, size.height)
        )

        drawRect(brush = brush)
    }
}