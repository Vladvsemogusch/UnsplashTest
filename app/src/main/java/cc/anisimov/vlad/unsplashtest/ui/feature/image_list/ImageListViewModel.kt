package cc.anisimov.vlad.unsplashtest.ui.feature.image_list

import androidx.lifecycle.viewModelScope
import cc.anisimov.vlad.unsplashtest.domain.interactor.AddPhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.DeletePhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.GetLatestPhotosInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.User
import cc.anisimov.vlad.unsplashtest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getLatestPhotosInteractor: GetLatestPhotosInteractor,
    private val addPhotoBookmarkInteractor: AddPhotoBookmarkInteractor,
    private val deletePhotoBookmarkInteractor: DeletePhotoBookmarkInteractor
) : BaseViewModel(), ImageListScreenActions {

    private val isLoadingFlow = MutableStateFlow(false)

    private val latestPhotosFlow = MutableStateFlow(listOf<Photo>())

    val screenState = combine(isLoadingFlow, latestPhotosFlow) { isLoading, latestPhotos ->
        if (isLoading) {
            ImageListScreenState.Loading
        } else {
            ImageListScreenState.Content(latestPhotos)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ImageListScreenState.Loading)

    init {
        viewModelScope.launch {
            isLoadingFlow.value = true
            latestPhotosFlow.value = getLatestPhotosInteractor()
            isLoadingFlow.value = false
        }
    }

    override fun onBookmarkClick(photo: Photo) {
        viewModelScope.launch {
            if (photo.isBookmarked) {
                deletePhotoBookmarkInteractor(photo.id)
            } else {
                addPhotoBookmarkInteractor(photo.id)
            }
        }
        //  Update current list
        val photoListCopy = latestPhotosFlow.value.toMutableList()
        val photoIndex = photoListCopy.indexOf(photo)
        photoListCopy.removeAt(photoIndex)
        photoListCopy.add(photoIndex, photo.copy(isBookmarked = !photo.isBookmarked))
        latestPhotosFlow.value = photoListCopy
    }

    override fun onAuthorClick(author: User) {
        sendEvent(ImageListScreenEvent.GoToAuthorProfile(author))
    }
}