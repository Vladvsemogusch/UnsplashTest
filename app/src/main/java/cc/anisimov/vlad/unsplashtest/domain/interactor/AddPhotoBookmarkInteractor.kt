package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepository
import cc.anisimov.vlad.unsplashtest.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPhotoBookmarkInteractor @Inject constructor(
    private val repository: PhotoRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(photoId: String) = withContext(dispatcher) {
        repository.addPhotoBookmark(photoId)
    }
}