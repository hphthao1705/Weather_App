package com.example.weather_app.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.weather_app.ui.WeatherAppTheme
import com.example.weather_app.ui.login.LoginBottomSheet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class OnboardingFragment : Fragment() {

    // callbacks set by the host Activity / NavHost
    var onButtonClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        // dispose the Composition when the Fragment's view is destroyed
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        setContent {
            WeatherAppTheme {
                var showLoginSheet by remember { mutableStateOf(false) }

                OnboardingScreen(
                    onButtonClick = onButtonClick,
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
        }
    }

    companion object {
        const val TAG = "OnboardingFragment"

        fun newInstance(
            onButtonClick: () -> Unit,
        ): OnboardingFragment = OnboardingFragment().apply {
            this.onButtonClick = onButtonClick
        }
    }
}