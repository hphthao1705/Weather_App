package com.example.weather_app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.weather_app.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.ui.home.HomeViewModel
import com.example.weather_app.ui.home.data.WeatherUiData
import com.example.weather_app.util.CustomFontFamily
import androidx.hilt.navigation.compose.hiltViewModel

private val cardBackgroundColor = Color(0xFF6151C3)
private val cardTextColor = Color(0xFFFFFFFFF)

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 26.dp)
    ) {
        HeaderSection(userName = "Thao Ho", onSearchClick = onSearchClick)

        Spacer(modifier = Modifier.height(26.dp))

        //Current location item
        WeatherItem(weather = WeatherUiData(state = "Ho Chi Minh", country = "Viet Nam", weatherCondition = "Mostly sunny", temperatureCelsius = 17))

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.recently_search),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 20.sp,
                lineHeight = 20.sp,
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        HistoryWeatherSearchSection(listOf())
    }
}

@Composable
private fun HeaderSection(userName: String, onSearchClick: () -> Unit ) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 43.dp)
    ) {
        WelcomeText(modifier = Modifier.weight(2f), userName = userName)

        Spacer(modifier = Modifier.width(33.dp))

        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { onSearchClick() },
            painter = painterResource(R.drawable.search_button),
            contentDescription = "Search Button"
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            modifier = Modifier.size(50.dp),
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

        Spacer(modifier = Modifier.height(8.dp))

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
private fun WeatherItem(weather: WeatherUiData) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = cardBackgroundColor)
            .padding(15.dp)
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
                color = cardTextColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weather.state,
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 28.sp,
                    lineHeight = 28.sp,
                ),
                color = cardTextColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = weather.weatherCondition,
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                ),
                color = cardTextColor
            )
        }

        //image + temperature in Celsius
        Column(modifier = Modifier.weight(1f)) {

        }
    }
}

@Composable
private fun HistoryWeatherSearchSection(history: List<WeatherUiData>) {

}

