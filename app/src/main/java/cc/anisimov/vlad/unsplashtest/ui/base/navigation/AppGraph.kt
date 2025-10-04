package cc.anisimov.vlad.unsplashtest.ui.base.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootGraph

@NavGraph<RootGraph>(
    defaultTransitions = DefaultAnimatedDestinationStyle::class,
    start = true
)
annotation class AppGraph