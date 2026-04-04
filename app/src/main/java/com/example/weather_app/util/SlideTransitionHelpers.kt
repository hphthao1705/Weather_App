package com.example.weather_app.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private const val TRANSITION_DURATION = 300

/** Slide in from the right (forward navigation). */
fun enterSlideIn(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}

/** Slide out to the left (forward navigation). */
fun exitSlideOut(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}

/** Slide in from the left (back navigation). */
fun popEnterSlideIn(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}

/** Slide out to the right (back navigation). */
fun popExitSlideOut(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}
