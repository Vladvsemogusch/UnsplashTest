package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepository
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLatestPhotosInteractor @Inject constructor(private val repository: PhotoRepository) {

    suspend operator fun invoke(): List<Photo>  = withContext(Dispatchers.IO){
        repository.getLatestPhotos()
    }
}