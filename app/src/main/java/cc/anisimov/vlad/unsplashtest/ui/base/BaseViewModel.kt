package cc.anisimov.vlad.unsplashtest.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val eventsChannel = Channel<UIEvent>()
    val eventsFlow = eventsChannel.receiveAsFlow()

    protected fun sendEvent(event: UIEvent) {
        viewModelScope.launch(Dispatchers.Main.immediate) { eventsChannel.send(event) }
    }
}
