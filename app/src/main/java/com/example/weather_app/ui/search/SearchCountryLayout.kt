package com.example.weather_app.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.ui.home.data.CountryUiData
import com.example.weather_app.ui.loading.shimmerLoading
import com.example.weather_app.ui.search.state.SearchUiState
import com.example.weather_app.ui.weatherDetails.WeatherDetailsScreen
import com.example.weather_app.util.CustomFontFamily

private val searchTheme = Color(0xFF6151C3)

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    onBackButtonClick: () -> Unit,
    onCountryClick: (CountryUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(searchTheme)
            .padding(top = 26.dp)
    ) {
        BackButton(onBackButtonClick = onBackButtonClick)
        Spacer(modifier = Modifier.height(20.dp))
        SearchSection()
        Spacer(modifier = Modifier.height(20.dp))

        val uiState by viewModel.uiState.collectAsState()
        when(uiState) {
            is SearchUiState.StandBy -> Unit
            is SearchUiState.Loading -> {
                SearchLoadingScreen()
            }
            is SearchUiState.Error -> {
                SearchErrorScreen(message = (uiState as SearchUiState.Error).message, onRetry = viewModel::getAllCountry)
            }
            is SearchUiState.Success -> {
                SearchResultsScreen(countries = (uiState as SearchUiState.Success).data, onItemClick = { country: CountryUiData? ->
                    if(country != null) {
                        onCountryClick(country)
                    } else {
                        // TODO - TH: handle null here
                    }
                })
            }
        }
    }
}

@Composable
private fun BackButton(onBackButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center // centers the text by default
    ) {
        // Back Icon (left aligned)
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart) // stick to the left
                .padding(start = 20.dp)
                .size(20.dp)
                .clip(CircleShape)
                .clickable { onBackButtonClick() },
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
private fun SearchSection() {
   var searchText by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(55.dp)
            .clip(CircleShape),
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                text = "Search your country",
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 14.sp,
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
private fun SearchResultsScreen(countries: List<CountryUiData>, onItemClick: (CountryUiData?) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp) // space between each item
    ) {
        items(
            count = countries.size,
            key = { index -> countries[index] }) { index ->
            val country = countries.getOrNull(index)
            SearchResultItem(country = country, onItemClick)
        }
    }
}

@Composable
private fun SearchResultItem(country: CountryUiData?, onItemClick: (CountryUiData?) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable {
                onItemClick(country)
            }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
            text = country?.name.orEmpty(),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 14.sp,
                color = Color.Gray
            )
        )
    }
}

// error
@Composable
fun SearchErrorScreen(
    message: String? = "Something went wrong",
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message.orEmpty(),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// loading
@Composable
private fun SearchLoadingScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .fillMaxWidth(0.6f)
                .height(16.dp)
                .shimmerLoading()
        )
    }
}
