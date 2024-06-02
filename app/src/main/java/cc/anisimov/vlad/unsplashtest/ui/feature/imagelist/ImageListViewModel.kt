package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import androidx.lifecycle.viewModelScope
import cc.anisimov.vlad.unsplashtest.domain.interactor.AddPhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.DeletePhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.User
import cc.anisimov.vlad.unsplashtest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val addPhotoBookmarkInteractor: AddPhotoBookmarkInteractor,
    private val deletePhotoBookmarkInteractor: DeletePhotoBookmarkInteractor,
    private val imageListPager: ImageListPager
) : BaseViewModel(), ImageListScreenActions {

    val screenState =
        combine(
            imageListPager.isLoadingFlow,
            imageListPager.imageListFlow
        ) { isLoading, latestPhotos ->
            when {
                latestPhotos.isEmpty() && isLoading -> {
                    ImageListScreenState.InitialLoading
                }

                latestPhotos.isNotEmpty() && isLoading -> {
                    ImageListScreenState.Content.LoadingMore(latestPhotos)
                }

                else -> {
                    ImageListScreenState.Content.Loaded(latestPhotos)
                    ImageListScreenState.Content.LoadingMore(latestPhotos)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ImageListScreenState.InitialLoading)

    init {
        viewModelScope.launch {
            imageListPager.loadMore()
        }
        handleImagePagerErrors()
    }

    private fun handleImagePagerErrors() {
        viewModelScope.launch {
            imageListPager.errorFlow.collect { throwable ->
                sendEvent(ImageListScreenEvent.ShowError(throwable.message))
            }
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
        imageListPager.updatePhotoBookmarkState(photo)
    }

    override fun onAuthorClick(author: User) {
        sendEvent(ImageListScreenEvent.GoToAuthorProfile(author))
    }

    override fun onListBottomItemReached() {
        viewModelScope.launch {
            if (!imageListPager.isLoadingFlow.value) {
                imageListPager.loadMore()
            }
        }
    }
}