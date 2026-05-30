package com.example.weather_app.ui.home.composeView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather_app.data.model.Country
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.util.AppDimension
import com.example.weather_app.util.CustomFontFamily
import com.example.weather_app.util.debugLog

@Composable
private fun CountryItem(country: Country) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimension.dimension_120)
            .clip(shape = RoundedCornerShape(AppDimension.dimension_20))
    ) {
        AsyncImage(
            model = country.flagResourceName,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.matchParentSize(),
            error = ColorPainter(WeatherTheme.colors.brandColor)
        )

        Text(
            text = country.name,
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 20.sp,
                lineHeight = 20.sp,
            ),
            color = WeatherTheme.colors.onBrandColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun HistoryWeatherSearchSection(modifier: Modifier = Modifier, history: List<Country>) {
    "history: $history".debugLog()
    Column(modifier = modifier) {
        history.forEach { country ->
            CountryItem(country = country)
            Spacer(modifier = Modifier.height(AppDimension.dimension_8))
        }
    }
}
