package com.example.weather_app.ui.weatherDetails

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weather_app.R
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.ui.weatherDetails.data.Card
import com.example.weather_app.ui.weatherDetails.data.WeatherUiData
import com.example.weather_app.ui.weatherDetails.state.WeatherUiState
import com.example.weather_app.util.CustomFontFamily
import com.example.weather_app.util.startOffsetForPage
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.ui.graphics.asComposeRenderEffect

@Composable
internal fun WeatherDetailsScreen(country: String, onBackButtonClick: () -> Unit) {
    val viewModel = hiltViewModel<WeatherViewModel>()
    viewModel.getWeather(country)

    val uiState by viewModel.uiState.collectAsState()
    when(uiState) {
        is WeatherUiState.Error -> {

        }
        is WeatherUiState.Loading -> {

        }
        is WeatherUiState.Success -> {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    // TODO - TH: currently, can't solve the padding system bars issue, so fix this height
                    .padding(top = 43.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                BackButton(onBackButtonClick = onBackButtonClick)

                Spacer(modifier = Modifier.height(30.dp))

                WeatherCarouselV2((uiState as WeatherUiState.Success).data)

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
private fun WeatherCarouselV2(weather: WeatherUiData) {
    val pagerState = rememberPagerState(pageCount = { weather.cards.size })

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = 1.dp,
            beyondViewportPageCount = weather.cards.size,
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(page + 2f)
                    .padding(
                        start = 64.dp,
                        end = 32.dp
                    )
                    .graphicsLayer {
                        val startOffset = pagerState.startOffsetForPage(page)
                        translationX = size.width * (startOffset * 99f)
                        alpha = (2f - startOffset) / 2
                         val blur = (startOffset * 20).coerceAtLeast(.1f)
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            renderEffect = RenderEffect
                                .createBlurEffect(blur, blur, Shader.TileMode.DECAL)
                                .asComposeRenderEffect()
                        }
                        val scale = 1f - (startOffset * .1f)
                        scaleX = scale
                        scaleY = scale
                    },
                contentAlignment = Alignment.Center
            ) {
                WeatherDetails(cardDetails = weather.cards.getOrNull(page))
            }
        }
    }
}


@Composable
private fun WeatherDetails(cardDetails: Card?) {
    if (cardDetails == null) return
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(WeatherTheme.colors.brandColor)
            .border(
                width = 1.dp,
                color = WeatherTheme.colors.brandColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
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
}

@Composable
private fun BackButton(onBackButtonClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_back_button),
        contentDescription = "Back",
        tint = WeatherTheme.colors.onBrandColor,
        modifier = Modifier
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
            .size(32.dp)
            .clip(CircleShape)
            .background(WeatherTheme.colors.brandColor)
            .clickable { onBackButtonClick() }
            .padding(8.dp)
    )
}
