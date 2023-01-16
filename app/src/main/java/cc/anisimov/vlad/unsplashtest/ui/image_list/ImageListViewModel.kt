package cc.anisimov.vlad.unsplashtest.ui.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cc.anisimov.vlad.unsplashtest.domain.interactor.GetLatestPhotosInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getLatestPhotosInteractor: GetLatestPhotosInteractor
) :
    ViewModel() {

    private val _latestPhotosFlow = MutableStateFlow(listOf<Photo>())
    val latestPhotosFlow = _latestPhotosFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _latestPhotosFlow.value = getLatestPhotosInteractor()
        }
    }
}