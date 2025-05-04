package com.example.weather_app.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

class OnBoardingFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ctx = context ?: return null
        return ComposeView(ctx).apply {
            //This is to ensure that the Compose Composition gets cleaned up when the Fragment's View is destroyed or detached from the Activity.
            // This is particularly important to avoid memory leaks or unnecessary processing when the Fragment is no longer in use.
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

            }
        }
    }
}