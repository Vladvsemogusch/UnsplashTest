package cc.anisimov.vlad.unsplashtest.util.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchAndCollect(lifecycleOwner: LifecycleOwner, flowCollector: FlowCollector<T>) {
    lifecycleOwner.lifecycleScope.launch {
        this@launchAndCollect.collect(flowCollector)
    }
}