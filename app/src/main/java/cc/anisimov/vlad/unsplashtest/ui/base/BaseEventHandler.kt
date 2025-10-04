package cc.anisimov.vlad.unsplashtest.ui.base

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("ComposableNaming")
@Composable
fun handleEvents(
    eventsFlow: Flow<UIEvent>,
    eventHandler: suspend (UIEvent) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val flowWithLifecycle = remember(lifecycleOwner, eventsFlow) {
        eventsFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    LaunchedEffect(eventsFlow) {
        flowWithLifecycle
            .onEach {
                launch { eventHandler(it) }
            }
            .flowOn(Dispatchers.Main.immediate)
            .collect()
    }
}