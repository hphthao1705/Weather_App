package com.example.weather_app.ui.weatherDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.ui.weatherDetails.data.Card
import com.example.weather_app.ui.weatherDetails.data.WeatherUiData
import com.example.weather_app.ui.weatherDetails.state.WeatherUiState
import com.example.weather_app.util.CustomFontFamily
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
internal fun WeatherDetailsScreen(country: String) {
    val viewModel = hiltViewModel<WeatherViewModel>()
    viewModel.getWeather(country)

    val uiState by viewModel.uiState.collectAsState()
    when(uiState) {
        is WeatherUiState.Error -> {

        }
        is WeatherUiState.Loading -> {

        }
        is WeatherUiState.Success -> {
            Column {
                Spacer(modifier = Modifier.height(30.dp))

                WeatherCarousel((uiState as WeatherUiState.Success).data)

                Text(
                    modifier = Modifier.padding(30.dp),
                    text = "Weather now",
                    style = TextStyle(
                        fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                        fontSize = 20.sp,
                        lineHeight = 20.sp
                    ),
                    color = WeatherTheme.colors.textPrimary
                )
            }
        }
        else -> Unit
    }
}

@Composable
fun WeatherCarousel(weather: WeatherUiData) {
    val radius = 160
    val stepAngle = (PI / 4).toFloat()

    var angleOffset by remember { mutableStateOf(-2 * stepAngle) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)     // reserve vertical layout space
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    angleOffset += dragAmount.y * 0.003f
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {

        val centerIndex = (-angleOffset / stepAngle).roundToInt()

        weather.cards.forEachIndexed { index, item ->

            val relative = index - centerIndex

            // Only show 3 cards
            if (abs(relative) > 1) return@forEachIndexed

            val angle = index * stepAngle + angleOffset

            val x = (radius * sin(angle)).toFloat()
            val y = (radius * cos(angle)).toFloat()

            val scale = (1f - abs(sin(angle)) * 0.3f).coerceIn(0.7f, 1f)

            Card(
                modifier = Modifier
                    .zIndex(scale)
                    .offset {
                        IntOffset(
                            x.roundToInt(),
                            (200 - y).roundToInt()
                        )
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = scale
                    }
            ) {
                Column(
                    modifier = Modifier
                        .background(WeatherTheme.colors.brandColor)
                        .padding(vertical = 20.dp, horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherDetails(item)
                }
            }
        }
    }
}


@Composable
private fun WeatherDetails(cardDetails: Card) {
    AsyncImage(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp),
        model = cardDetails.image,
        contentDescription = "Weather image"
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = cardDetails.location,
        style = TextStyle(
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 18.sp,
            lineHeight = 18.sp
        ),
        color = Color.White
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = cardDetails.temperature,
        style = TextStyle(
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 50.sp,
            lineHeight = 50.sp
        ),
        color = Color.White
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = cardDetails.condition,
        style = TextStyle(
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 18.sp,
            lineHeight = 18.sp
        ),
        color = Color.White
    )
}