package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepository
import cc.anisimov.vlad.unsplashtest.di.DispatcherIO
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLatestPhotosPageInteractor @Inject constructor(
    private val repository: PhotoRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(page: Int): List<Photo> = withContext(dispatcher) {
        repository.getLatestPhotos(page)
    }
}