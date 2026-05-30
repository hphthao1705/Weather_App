package com.example.weather_app.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val TRANSITION_DURATION = 300

/** Slide in from the right (forward navigation). */
fun <T> enterSlideIn(): (AnimatedContentTransitionScope<T>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}

/** Slide out to the left (forward navigation). */
fun <T> exitSlideOut(): (AnimatedContentTransitionScope<T>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}

/** Slide in from the left (back navigation). */
fun <T> popEnterSlideIn(): (AnimatedContentTransitionScope<T>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}

/** Slide out to the right (back navigation). */
fun <T> popExitSlideOut(): (AnimatedContentTransitionScope<T>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(TRANSITION_DURATION)
    )
}