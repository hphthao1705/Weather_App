package com.example.weather_app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.home.HomeScreen
import com.example.weather_app.ui.onboarding.OnboardingScreen
import androidx.compose.runtime.getValue

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContent {
            //TODO: apply Navigation 3 later
            val navController = rememberNavController()
            val state: ScreenState by viewModel.screenState.collectAsStateWithLifecycle()

            LaunchedEffect(state) {
                Log.d("app", "state: $state")
                when(state) {
                    is ScreenState.NavigateTo -> {
                        navController.navigate((state as ScreenState.NavigateTo).route)
                    }
                    else -> Unit
                }
            }

            WeatherAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreen.ON_BOARDING_SCREEN.name,
                ) {
                    composable(route = NavigationScreen.ON_BOARDING_SCREEN.name) {
                        OnboardingScreen(onButtonClick = {
                            viewModel.updateScreenState(route = NavigationScreen.HOME_SCREEN.name)
                        }, onLogInClick = {})
                    }

                    composable(route = NavigationScreen.HOME_SCREEN.name) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    WeatherAppTheme(darkTheme = true) {
        Text(
            text = "Hello Jetpack Compose!",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}