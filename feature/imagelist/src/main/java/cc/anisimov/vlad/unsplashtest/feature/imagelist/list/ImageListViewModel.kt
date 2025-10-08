package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import androidx.lifecycle.viewModelScope
import cc.anisimov.vlad.unsplashtest.core.ui.base.BaseViewModel
import cc.anisimov.vlad.unsplashtest.domain.interactor.AddPhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.DeletePhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.feature.imagelist.mapper.PhotoMapper
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.PhotoUi
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.UserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel
@Inject
constructor(
    private val addPhotoBookmarkInteractor: AddPhotoBookmarkInteractor,
    private val deletePhotoBookmarkInteractor: DeletePhotoBookmarkInteractor,
    private val imageListPager: ImageListPager,
    private val photoMapper: PhotoMapper,
) : BaseViewModel(),
    ImageListScreenActions {
    private val imageListFlow = imageListPager.imageListFlow.map { photoMapper.map(it) }
    val screenState =
        combine(
            imageListPager.isLoadingFlow,
            imageListFlow,
        ) { isLoading, latestPhotos ->
            val immutableLatestPhotos = latestPhotos.toPersistentList()
            when {
                latestPhotos.isEmpty() && isLoading -> {
                    ImageListScreenState.InitialLoading
                }

                latestPhotos.isNotEmpty() && isLoading -> {
                    ImageListScreenState.Content.LoadingMore(immutableLatestPhotos)
                }

                else -> {
                    ImageListScreenState.Content.Ready(immutableLatestPhotos)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ImageListScreenState.InitialLoading)

    init {
        //  Initial load
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

    override fun onBookmarkClick(photo: PhotoUi) {
        viewModelScope.launch {
            if (photo.isBookmarked) {
                deletePhotoBookmarkInteractor(photo.id)
            } else {
                addPhotoBookmarkInteractor(photo.id)
            }
        }
    }

    override fun onAuthorClick(author: UserUi) {
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
