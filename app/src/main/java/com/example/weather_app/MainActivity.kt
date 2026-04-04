package com.example.weather_app

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.home.HomeScreen
import com.example.weather_app.ui.login.LoginBottomSheet
import com.example.weather_app.ui.onboarding.OnboardingScreen
import com.example.weather_app.ui.search.SearchScreen
import com.example.weather_app.ui.weatherDetails.WeatherDetailsScreen
import com.example.weather_app.util.enterSlideIn
import com.example.weather_app.util.exitSlideOut
import com.example.weather_app.util.popEnterSlideIn
import com.example.weather_app.util.popExitSlideOut
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle camera cutout
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)

        )
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)

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
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()
        ) {
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
                    onDismiss = { showLoginSheet = false }
                )
            }
        }

        composable(
            "home",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()) {
            HomeScreen(onSearchClick = { navController.navigate("search") })
        }

        composable(
            "search",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()) {
            SearchScreen(
                viewModel = hiltViewModel(),
                onBackButtonClick = {
                    navController.popBackStack()
                },
                onCountryClick = { country ->
                    navController.navigate("weatherDetails/${country.name}")
                }
            )
        }

        composable(
            route = "weatherDetails/{country}",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()) { backStackEntry ->
            val country = backStackEntry.arguments?.getString("country")
            WeatherDetailsScreen(
                country = country.orEmpty(),
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
