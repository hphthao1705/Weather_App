package com.example.weather_app.ui.home.composeView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_app.R
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.ui.home.HomeViewModel
import com.example.weather_app.ui.home.data.HomeWeatherUiData
import com.example.weather_app.util.AppDimension
import com.example.weather_app.util.CustomFontFamily

@Composable
internal fun HomeScreen(
    onSearchClick: () -> Unit,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val searchHistory by viewModel.historyCountryData.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(horizontal = AppDimension.dimension_26)
        .verticalScroll(rememberScrollState())
    ) {
        HeaderSection(userName = "Thao Ho", onSearchClick = onSearchClick)

        Spacer(modifier = Modifier.height(AppDimension.dimension_26))

        //Current location item
        WeatherItem(weather = HomeWeatherUiData(state = "Ho Chi Minh", country = "Viet Nam", weatherCondition = "Mostly sunny", temperatureCelsius = 17))

        Spacer(modifier = Modifier.height(AppDimension.dimension_50))

        Text(
            text = stringResource(R.string.recently_search),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 20.sp,
                lineHeight = 20.sp,
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        HistoryWeatherSearchSection(history = searchHistory)
    }
}

@Composable
private fun HeaderSection(userName: String, onSearchClick: () -> Unit ) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = AppDimension.dimension_43)
    ) {
        WelcomeText(modifier = Modifier.weight(2f), userName = userName)

        Spacer(modifier = Modifier.width(AppDimension.dimension_33))

        Image(
            modifier = Modifier
                .size(AppDimension.dimension_50)
                .clip(CircleShape)
                .clickable { onSearchClick() },
            painter = painterResource(R.drawable.search_button),
            contentDescription = "Search Button"
        )

        Spacer(modifier = Modifier.width(AppDimension.dimension_8))

        Image(
            modifier = Modifier.size(AppDimension.dimension_50),
            painter = painterResource(R.drawable.web_button) ,
            contentDescription = "Website Button"
        )
    }
}

@Composable
private fun WelcomeText(userName: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Hello $userName,",
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 20.sp,
                lineHeight = 20.sp,
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(AppDimension.dimension_8))

        Text(
            text = stringResource(R.string.discover_the_weather),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 20.sp,
                lineHeight = 20.sp,
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun WeatherItem(weather: HomeWeatherUiData) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(AppDimension.dimension_20))
            .background(color = WeatherTheme.colors.brandColor)
            .padding(AppDimension.dimension_15)
    ) {
        //country + state + weather condition
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.current_location),
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                ),
                color = WeatherTheme.colors.onBrandColor
            )

            Spacer(modifier = Modifier.height(AppDimension.dimension_8))

            Text(
                text = weather.state,
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 28.sp,
                    lineHeight = 28.sp,
                ),
                color = WeatherTheme.colors.onBrandColor
            )

            Spacer(modifier = Modifier.height(AppDimension.dimension_20))

            Text(
                text = weather.weatherCondition,
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                ),
                color = WeatherTheme.colors.onBrandColor
            )
        }

        //image + temperature in Celsius
        Column(modifier = Modifier.weight(1f)) {

        }
    }
}
