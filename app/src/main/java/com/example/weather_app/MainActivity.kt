package com.example.weather_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.home.HomeScreen
import com.example.weather_app.ui.onboarding.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContent {
            WeatherAppTheme {
                AppNavGraph()
            }
        }
    }
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnboardingScreen(
                onButtonClick = {
                    navController.navigate("home") {
                        popUpTo("onboarding") {
                            inclusive = true
                        } // remove Onboarding from back stack
                    }
                },
                onLogInClick = {}
            )
        }
        composable("home") {
//            HomeScreen(onNavigateToDetail = { navController.navigate("detail") })
            HomeScreen()
        }
//        composable("detail") {
//            DetailScreen(onBack = { navController.popBackStack() })
//        }
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