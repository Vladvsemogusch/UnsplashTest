package cc.anisimov.vlad.unsplashtest.ui.base.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object DefaultAnimatedDestinationStyle : DestinationStyle.Animated() {

    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
        {
            fadeIn(
                animationSpec = tween(DEFAULT_ANIM_DURATION)
            ) + slideIn(
                animationSpec = tween(DEFAULT_ANIM_DURATION),
                initialOffset = { contentSize ->
                    IntOffset(x = contentSize.width / 2, y = 0)
                }
            )
        }

    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? =
        {
            fadeOut(animationSpec = tween(DEFAULT_ANIM_DURATION))
        }

    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
        {
            EnterTransition.None
        }

    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? =
        {
            fadeOut(
                animationSpec = tween(DEFAULT_ANIM_DURATION)
            ) + slideOut(
                animationSpec = tween(DEFAULT_ANIM_DURATION),
                targetOffset = { contentSize ->
                    IntOffset(x = contentSize.width / 2, y = 0)
                })
        }

    const val DEFAULT_ANIM_DURATION = 300
}