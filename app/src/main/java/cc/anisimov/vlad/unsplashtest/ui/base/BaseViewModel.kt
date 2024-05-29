package cc.anisimov.vlad.unsplashtest.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _event = Channel<UIEvent>()
    val eventsFlow = _event.receiveAsFlow()

    protected fun sendEvent(event: UIEvent) {
        viewModelScope.launch(Dispatchers.Main.immediate) { _event.send(event) }
    }
}