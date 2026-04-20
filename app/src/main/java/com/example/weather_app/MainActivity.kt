package com.example.weather_app

import android.graphics.Color
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.home.composeView.HomeScreen
import com.example.weather_app.ui.home.data.CountryUiData
import com.example.weather_app.ui.login.LoginBottomSheet
import com.example.weather_app.ui.onboarding.composeView.OnboardingScreen
import com.example.weather_app.ui.search.composeView.SearchScreen
import com.example.weather_app.ui.weatherDetails.composeView.WeatherDetailsScreen
import com.example.weather_app.util.enterSlideIn
import com.example.weather_app.util.exitSlideOut
import com.example.weather_app.util.popEnterSlideIn
import com.example.weather_app.util.popExitSlideOut
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        supportActionBar?.hide()

        // Single entry point — no more manual fragment transactions
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

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        // 1. Onboarding — start destination, removed from back stack on proceed
        composable(
            "onboarding",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()
        ) {
            var showLoginSheet by remember { mutableStateOf(false) }

            OnboardingScreen(
                onButtonClick = {
                    navController.navigate("home") {
                        // Clear onboarding off the back stack so back button doesn't return to it
                        popUpTo("onboarding") { inclusive = true }
                    }
                },
                onLogInClick = { showLoginSheet = true }
            )

            if (showLoginSheet) {
                LoginBottomSheet(
                    onDismiss = { showLoginSheet = false }
                )
            }
        }

        // 2. Home
        composable(
            "home",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()
        ) {
            HomeScreen(
                onSearchClick = { navController.navigate("search") }
            )
        }

        // 3. Search
        composable(
            "search",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()
        ) {
            SearchScreen(
                viewModel = hiltViewModel(),
                onBackButtonClick = { navController.popBackStack() },
                onCountryClick = { country ->
                    navController.navigate("weatherDetails/${country.name}")
                }
            )
        }

        // 4. Weather Details
        composable(
            route = "weatherDetails/{country}",
            enterTransition = enterSlideIn(),
            exitTransition = exitSlideOut(),
            popEnterTransition = popEnterSlideIn(),
            popExitTransition = popExitSlideOut()
        ) { backStackEntry ->
            val country = backStackEntry.arguments?.getString("country")
            WeatherDetailsScreen(
                cityName = country.orEmpty(),
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    }
}
