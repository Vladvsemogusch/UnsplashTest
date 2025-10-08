package cc.anisimov.vlad.unsplashtest.navigation

import com.ramcosta.composedestinations.annotation.ExternalNavGraph
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.imagelist.navgraphs.ImageListNavGraph

@NavHostGraph(defaultTransitions = DefaultAnimatedDestinationStyle::class)
annotation class AppGraph {
    @ExternalNavGraph<ImageListNavGraph>(start = true)
    companion object Includes
}
