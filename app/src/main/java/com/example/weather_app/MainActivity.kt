package com.example.weather_app

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.home.composeView.HomeScreen
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
        startDestination = "navigation_flow"
    ) {

        composable("navigation_flow") {
            AppNavigation()
        }
    }
}

@Composable
private fun AppNavigation(
    sharedViewModel: SharedFlowViewModel = hiltViewModel()
) {
    val currentScreen by sharedViewModel.navEvents.collectAsState()
    var showLoginSheet by remember { mutableStateOf(false) }

    // TODO - TH: handle this
    BackHandler(enabled = true) {

    }

    AnimatedContent(
        targetState = currentScreen,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            // compare the order to know it back navigation or not
            if (targetState.order > initialState.order) {
               enterSlideIn<NavEventState>() togetherWith exitSlideOut<NavEventState>()
            } else {
                popEnterSlideIn<NavEventState>() togetherWith popExitSlideOut<NavEventState>()
            }
        },
        label = "NavigationFlowAnimation"
    ) { targetScreen ->
        when (targetScreen) {
            NavEventState.GoToOnBoarding -> {
                OnboardingScreen(
                    onButtonClick = {
                        sharedViewModel.onGoToHome()
                    },
                    onLogInClick = { showLoginSheet = true }
                )
            }
            NavEventState.GoToHome -> {
                HomeScreen(
                    onSearchClick = { sharedViewModel.onGoToSearch() }
                )
            }
            NavEventState.GoToLogin -> {
                LoginBottomSheet(
                    onDismiss = { showLoginSheet = false }
                )
            }
            NavEventState.GoToSearch -> {
                SearchScreen(
                    viewModel = hiltViewModel(),
                    onBackButtonClick = { sharedViewModel.goBack() },
                    onCountryClick = { country ->
                        sharedViewModel.onGoToWeatherDetails(country.name)
                    }
                )
            }
            is NavEventState.GoToWeatherDetails -> {
                WeatherDetailsScreen(
                    cityName = (currentScreen as NavEventState.GoToWeatherDetails).cityName,
                    onBackButtonClick = { sharedViewModel.goBack() }
                )
            }
        }
    }
}
