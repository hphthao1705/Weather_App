package com.example.weather_app.ui.search.composeView

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.ui.home.data.CountryUiData
import com.example.weather_app.ui.loading.shimmerLoading
import com.example.weather_app.ui.search.SearchViewModel
import com.example.weather_app.ui.search.state.ErrorType
import com.example.weather_app.ui.search.state.SearchUiState
import com.example.weather_app.util.AppDimension
import com.example.weather_app.util.CustomFontFamily
import kotlinx.coroutines.launch

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    onBackButtonClick: () -> Unit,
    onCountryClick: (CountryUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .background(WeatherTheme.colors.brandColor)
            .padding(top = AppDimension.dimension_43)
    ) {
        Spacer(modifier = Modifier.height(AppDimension.dimension_5))
        BackButton(onBackButtonClick = onBackButtonClick)
        Spacer(modifier = Modifier.height(AppDimension.dimension_20))
        SearchSection(onTyping = { query ->
            if (query.length < 3) {
                viewModel.filterCountryByName(isShowAll = true)
                return@SearchSection
            }
            viewModel.filterCountryByName(query = query)
        })
        Spacer(modifier = Modifier.height(AppDimension.dimension_20))

        val uiState by viewModel.uiState.collectAsState()
        when(uiState) {
            is SearchUiState.StandBy -> Unit
            is SearchUiState.Loading -> {
                SearchLoadingScreen()
            }
            is SearchUiState.Error -> {
                SearchErrorScreen(
                    title = (uiState as SearchUiState.Error).title,
                    message = (uiState as SearchUiState.Error).content,
                    showRetry = (uiState as SearchUiState.Error).errorType == ErrorType.NO_INTERNET,
                    onRetry = viewModel::getAllCountry
                )
            }
            is SearchUiState.Success -> {
                SearchResultsScreen(
                    countries = (uiState as SearchUiState.Success).data,
                    onItemClick = { country: CountryUiData ->
                        viewModel.insertCountry(country)
                        onCountryClick(country)
                    }
                )
            }
        }
    }
}

@Composable
private fun BackButton(onBackButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppDimension.dimension_10),
        contentAlignment = Alignment.Center // centers the text by default
    ) {
        // Back Icon (left aligned)
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart) // stick to the left
                .padding(start = AppDimension.dimension_20)
                .size(AppDimension.dimension_38)
                .clip(CircleShape)
                .clickable { onBackButtonClick() }
                // padding inside
                .padding(AppDimension.dimension_8),
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "Icon back",
            tint = Color.White
        )

        // Centered Text
        Text(
            text = "Search your country",
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 20.sp,
                lineHeight = 20.sp
            ),
            color = Color.White
        )
    }
}

@Composable
private fun SearchSection(onTyping:(String) -> Unit) {
   var searchText by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimension.dimension_10)
            .height(AppDimension.dimension_55)
            .clip(CircleShape),
        value = searchText,
        onValueChange = { value: String ->
            searchText = value
            onTyping(value)
        },
        placeholder = {
            Text(
                text = "Your country name",
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 17.sp,
                    color = Color.Gray
                )
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 14.sp,
            color = Color.Black
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Black // change cursor name
        )
    )
}

@Composable
private fun SearchResultsScreen(countries: List<CountryUiData>, onItemClick: (CountryUiData) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(AppDimension.dimension_4) // space between each item
    ) {
        itemsIndexed(
            items = countries,
            contentType = { _, _ -> "country_item" },
            key = { index, country -> country.name ?: index }
        ) { _, country ->
            SearchResultItem(country = country, onItemClick)
        }
    }
}

@Composable
private fun SearchResultItem(country: CountryUiData, onItemClick: (CountryUiData) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable {
                onItemClick(country)
            }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = AppDimension.dimension_20, vertical = AppDimension.dimension_5),
            text = country.name.orEmpty(),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 17.sp,
                color = Color.Gray
            )
        )
    }
}

// error
@Composable
fun SearchErrorScreen(
    title: String,
    message: String,
    showRetry: Boolean = true,
    onRetry: () -> Unit
) {
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WeatherTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = AppDimension.dimension_24)
        ) {

            // Icon circle
            Box(
                modifier = Modifier
                    .size(AppDimension.dimension_120)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cloud_off),
                    contentDescription = "Error icon",
                    tint = WeatherTheme.colors.brandColor,
                    modifier = Modifier.size(AppDimension.dimension_48)
                )
            }

            Spacer(modifier = Modifier.height(AppDimension.dimension_28))

            Text(
                text = title,
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppDimension.dimension_12))

            Text(
                text = message,
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
            )

            if(showRetry) {
                Spacer(modifier = Modifier.height(AppDimension.dimension_28))

                Button(
                    onClick = {
                        // when user click on Retry button, set the rotation value to 360 for rotate retry icon
                        scope.launch {
                            rotation.animateTo(
                                targetValue = rotation.value + 360f, // ddd 360 to current for a full spin
                                animationSpec = tween(
                                    durationMillis = 600,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                        onRetry()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WeatherTheme.colors.brandColor
                    ),
                    contentPadding = PaddingValues(
                        horizontal = AppDimension.dimension_28,
                        vertical = AppDimension.dimension_12
                    )
                ) {
                    Icon(
                        // apply the rotation value
                        modifier = Modifier.rotate(rotation.value),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Retry icon",
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(AppDimension.dimension_8))

                    Text(
                        text = "Retry",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// loading
@Composable
private fun SearchLoadingScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppDimension.dimension_4)
    ) {
        items(10) {
            SearchResultItemLoading()
        }
    }
}

@Composable
private fun SearchResultItemLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimension.dimension_35)
                .shimmerLoading()
        )
    }
}
