package com.example.weather_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.home.HomeScreen
import com.example.weather_app.ui.onboarding.OnboardingScreen
import com.example.weather_app.ui.search.SearchScreen
import com.example.weather_app.ui.weatherDetails.WeatherDetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import com.example.weather_app.ui.login.LoginBottomSheet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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
        composable("onboarding",
            enterTransition = {
                // Open screen from right to left
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                // Exit from the screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                // Come back to this screen => back press
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                // When popping away from this screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }) {
            var showLoginSheet by remember { mutableStateOf(false) }

            OnboardingScreen(
                onButtonClick = {
                    navController.navigate("home") {
                        // remove Onboarding from back stack
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                },
                onLogInClick = {
                    showLoginSheet = true
                }
            )

            if (showLoginSheet) {
                LoginBottomSheet(
                    showSheet = showLoginSheet,
                    onDismiss = { showLoginSheet = false }
                )
            }
        }

        composable(
            "home",
            enterTransition = {
                // Open screen from right to left
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                // Exit from the screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                // Come back to this screen => back press
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                // When popping away from this screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }) {
            HomeScreen(onSearchClick = { navController.navigate("search") })
        }

        composable(
            "search",
            enterTransition = {
                // Open screen from right to left
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                // Exit from the screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                // Come back to this screen => back press
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                // When popping away from this screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }) {
            SearchScreen(
                viewModel = hiltViewModel(),
                onBackButtonClick = { navController.popBackStack() },
                onCountryClick = { country ->
                    navController.navigate("weatherDetails/${country.name}")
                }
            )
        }

        composable(
            route = "weatherDetails/{country}", enterTransition = {
                // Open screen from right to left
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                // Exit from the screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                // Come back to this screen => back press
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                // When popping away from this screen
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }) { backStackEntry ->
            val country = backStackEntry.arguments?.getString("country")
            WeatherDetailsScreen(country = country.orEmpty())
        }
    }
}
