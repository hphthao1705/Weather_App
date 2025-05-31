package com.example.weather_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.onboarding.OnboardingScreen

enum class NavigationScreen {
    ON_BOARDING_SCREEN
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContent {
//            val backStack = rememberNavBackStack(RouteA)
//
//            NavDisplay(
//                backStack = backStack,
//                onBack = { backStack.removeLastOrNull() },
//                entryProvider = { key ->
//                    when(key) {
//                        is RouteA -> NavEntry(key) {
//                            OnboardingScreen(onButtonClick = {}, onLogInClick = {})
//                        }
//
//                        else -> {
//                            error("Unknown route: $key")
//                        }
//                    }
//                }
//            )

            //TODO: apply Navigation 3 later
            val navController = rememberNavController()
            WeatherAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreen.ON_BOARDING_SCREEN.name,

                ) {
                    composable(route = NavigationScreen.ON_BOARDING_SCREEN.name) {
                        OnboardingScreen(onButtonClick = {}, onLogInClick = {})
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