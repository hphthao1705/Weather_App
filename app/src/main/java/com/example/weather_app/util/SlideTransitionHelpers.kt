package com.example.weather_app.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val TRANSITION_DURATION = 300

/** Slide in from the right (forward navigation). */
fun <T> AnimatedContentTransitionScope<T>.enterSlideIn(): EnterTransition =
    slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(TRANSITION_DURATION))

/** Slide out to the left (forward navigation). */
fun <T> AnimatedContentTransitionScope<T>.exitSlideOut(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(TRANSITION_DURATION))

/** Slide in from the left (back navigation). */
fun <T> AnimatedContentTransitionScope<T>.popEnterSlideIn(): EnterTransition =
    slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(TRANSITION_DURATION))

/** Slide out to the right (back navigation). */
fun <T> AnimatedContentTransitionScope<T>.popExitSlideOut(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(TRANSITION_DURATION))