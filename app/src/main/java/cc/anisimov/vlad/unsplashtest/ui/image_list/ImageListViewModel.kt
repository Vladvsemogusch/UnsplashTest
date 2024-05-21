package cc.anisimov.vlad.unsplashtest.ui.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cc.anisimov.vlad.unsplashtest.domain.interactor.AddPhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.DeletePhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.GetLatestPhotosInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getLatestPhotosInteractor: GetLatestPhotosInteractor,
    private val addPhotoBookmarkInteractor: AddPhotoBookmarkInteractor,
    private val deletePhotoBookmarkInteractor: DeletePhotoBookmarkInteractor
) : ViewModel() {

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _latestPhotosFlow = MutableStateFlow(listOf<Photo>())
    val latestPhotosFlow = _latestPhotosFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoadingFlow.value = true
            _latestPhotosFlow.value = getLatestPhotosInteractor()
            _isLoadingFlow.value = false
        }
    }

    fun onBookmarkClick(photo: Photo) {
        viewModelScope.launch {
            if (photo.isBookmarked) {
                deletePhotoBookmarkInteractor(photo.id)
            } else {
                addPhotoBookmarkInteractor(photo.id)
            }
        }
        //  Update current list
        val currentPhotoList = latestPhotosFlow.value.toMutableList()
        val photoIndex = currentPhotoList.indexOf(photo)
        currentPhotoList.removeAt(photoIndex)
        currentPhotoList.add(photoIndex, photo.copy(isBookmarked = !photo.isBookmarked))
        _latestPhotosFlow.value = currentPhotoList
    }
}