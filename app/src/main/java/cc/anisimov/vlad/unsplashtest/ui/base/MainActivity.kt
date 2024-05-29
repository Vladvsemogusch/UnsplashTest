package cc.anisimov.vlad.unsplashtest.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cc.anisimov.vlad.unsplashtest.ui.feature.NavGraphs
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnsplashTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            engine = assembleNavHostEngine()
                        )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    @Composable
    private fun assembleNavHostEngine(): NavHostEngine {
        return rememberAnimatedNavHostEngine(
            navHostContentAlignment = Alignment.TopCenter,
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = {
                    fadeIn(animationSpec = tween(DEFAULT_ANIM_DURATION)) +
                            slideIn(
                                animationSpec = tween(DEFAULT_ANIM_DURATION),
                                initialOffset = { contentSize ->
                                    IntOffset(
                                        contentSize.width / 2,
                                        0
                                    )
                                }
                            )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(20000))
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(DEFAULT_ANIM_DURATION)) +
                            slideOut(
                                animationSpec = tween(DEFAULT_ANIM_DURATION),
                                targetOffset = { contentSize ->
                                    IntOffset(
                                        contentSize.width / 2,
                                        0
                                    )
                                })
                }
            )
        )
    }


    companion object {
        const val DEFAULT_ANIM_DURATION = 350
    }
}