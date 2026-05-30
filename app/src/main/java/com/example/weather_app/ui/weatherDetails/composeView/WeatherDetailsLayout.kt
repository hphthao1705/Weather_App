package com.example.weather_app.ui.weatherDetails.composeView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weather_app.R
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.ui.weatherDetails.WeatherViewModel
import com.example.weather_app.ui.weatherDetails.data.Card
import com.example.weather_app.ui.weatherDetails.data.WeatherUiData
import com.example.weather_app.ui.weatherDetails.state.WeatherUiState
import com.example.weather_app.util.AppDimension
import com.example.weather_app.util.CustomFontFamily

@Composable
internal fun WeatherDetailsScreen(cityName: String?, onBackButtonClick: () -> Unit) {
    val viewModel = hiltViewModel<WeatherViewModel>()
    viewModel.getWeather(cityName = cityName)

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
                    .padding(top = AppDimension.dimension_43)
            ) {
                Spacer(modifier = Modifier.height(AppDimension.dimension_10))

                HeaderRow(onBackButtonClick = onBackButtonClick, cityName = cityName)

                Spacer(modifier = Modifier.height(AppDimension.dimension_30))

                WeatherCarouselV3((uiState as WeatherUiState.Success).data)

                Text(
                    modifier = Modifier.padding(AppDimension.dimension_30),
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

/**
 * https://developer.android.com/develop/ui/compose/components/carousel?hl=vi
 * This material API is experimental and is likely to change or to be removed in the future.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeatherCarouselV3(weather: WeatherUiData) {
    val spacing = AppDimension.dimension_8
    val horizontalPadding = AppDimension.dimension_15
    val visibleItemsInScreen = 2

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimension.dimension_15)
    ) {
        val itemWidth = (maxWidth - (horizontalPadding * 2) - (spacing * (visibleItemsInScreen - 1))) / visibleItemsInScreen

        HorizontalUncontainedCarousel(
            state = rememberCarouselState { weather.cards.count() },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            itemWidth  = itemWidth,
            itemSpacing = AppDimension.dimension_8
        ) { index ->
            val card = weather.cards.getOrNull(index)
            if (card != null) {
                WeatherDetails(cardDetails = card)
            }
        }
    }
}

@Composable
private fun WeatherDetails(cardDetails: Card?) {
    if (cardDetails == null) return

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(AppDimension.dimension_15))
            .background(WeatherTheme.colors.brandColor)
            .border(
                width = AppDimension.dimension_1,
                color = WeatherTheme.colors.brandColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(AppDimension.dimension_16)
            )
            .padding(AppDimension.dimension_20),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        AsyncImage(
            modifier = Modifier
                .width(AppDimension.dimension_150)
                .height(AppDimension.dimension_150),
            model = cardDetails.image,
            contentDescription = "Weather image"
        )

        Spacer(modifier = Modifier.height(AppDimension.dimension_12))

        Text(
            text = cardDetails.location,
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 18.sp,
                lineHeight = 18.sp
            ),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(AppDimension.dimension_12))

        Text(
            text = cardDetails.temperature,
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 50.sp,
                lineHeight = 50.sp
            ),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(AppDimension.dimension_12))

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
private fun HeaderRow(onBackButtonClick: () -> Unit, cityName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppDimension.dimension_10),
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = AppDimension.dimension_20)
                .size(AppDimension.dimension_32)
                .clip(CircleShape)
                .background(WeatherTheme.colors.brandColor)
                .clickable { onBackButtonClick() }
                .padding(AppDimension.dimension_8),
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "Back icon",
            tint = WeatherTheme.colors.onBrandColor,
        )

        Spacer(modifier = Modifier.width(AppDimension.dimension_10))

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = cityName,
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 24.sp,
                lineHeight = 24.sp
            ),
            color = WeatherTheme.colors.textPrimary
        )
    }
}

@Composable
private fun WeatherNowCard(weather: WeatherUiData) {

}
