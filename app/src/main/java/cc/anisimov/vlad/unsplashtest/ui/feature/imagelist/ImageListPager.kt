package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import cc.anisimov.vlad.unsplashtest.domain.interactor.GetLatestPhotosPageInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.util.tryCoroutine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ImageListPager @Inject constructor(private val getLatestPhotosPageInteractor: GetLatestPhotosPageInteractor) {

    //  Page index starts from 1; 0 means none is loaded yet.
    //  Could be remade as state, but current case it too simple to benefit from it.
    private var lastPage = 0

    private val _imageListFlow = MutableStateFlow<List<Photo>>(emptyList())
    val imageListFlow = _imageListFlow.asStateFlow()

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()
    suspend fun loadMore() {
        _isLoadingFlow.value = true
        tryCoroutine(
            tryFun = {
                _imageListFlow.update { photoList ->
                    photoList + getLatestPhotosPageInteractor(++lastPage)
                }
            },
            catchFun =
            { throwable ->
                _errorFlow.emit(throwable)
            })
        _isLoadingFlow.value = false
    }

    fun updatePhotoBookmarkState(photo: Photo) {
        _imageListFlow.update { photoList ->
            photoList.map {
                if (it.id == photo.id) it.copy(isBookmarked = !photo.isBookmarked) else it
            }
        }
    }
}