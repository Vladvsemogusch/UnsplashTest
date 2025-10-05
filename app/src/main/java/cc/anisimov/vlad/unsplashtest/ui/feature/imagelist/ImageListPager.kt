package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import cc.anisimov.vlad.unsplashtest.domain.interactor.GetLatestPhotosPageInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.util.tryCoroutine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import javax.inject.Inject

class ImageListPager @Inject constructor(private val getLatestPhotosPageInteractor: GetLatestPhotosPageInteractor) {

    //  Page index starts from 1; 0 means none is loaded yet.
    //  Could be remade as state, but current case it too simple to benefit from it.
    private var lastPage = 0

    private val _imagePageFlows = MutableSharedFlow<Flow<List<Photo>>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val imageListFlow = _imagePageFlows
        .flattenMerge(concurrency = Int.MAX_VALUE)
        .runningFold(emptyMap<String, Photo>()) { photoMap, newPhotos ->
            photoMap
                .toMutableMap()
                .apply {
                    newPhotos.forEach { photo -> this[photo.id] = photo }
                }
        }
        .map { it.values.toList() }

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()
    suspend fun loadMore() {
        _isLoadingFlow.value = true
        tryCoroutine(
            tryFun = {
                val nextPageFlow = getLatestPhotosPageInteractor(++lastPage)
                _imagePageFlows.emit(nextPageFlow)
            },
            catchFun = { throwable ->
                _errorFlow.emit(throwable)
            }
        )
        _isLoadingFlow.value = false
    }
}