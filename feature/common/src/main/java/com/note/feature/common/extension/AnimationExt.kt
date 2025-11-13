package com.note.feature.common.extension

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

fun AnimatedContentTransitionScope<*>.slideInLeft(
    durationMillis: Int = 300
): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
    )
}

fun AnimatedContentTransitionScope<*>.slideOutRight(
    durationMillis: Int = 300
): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
    )
}