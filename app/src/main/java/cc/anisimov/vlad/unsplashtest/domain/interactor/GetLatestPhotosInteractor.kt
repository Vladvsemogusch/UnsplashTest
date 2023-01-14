package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.UnsplashRepository
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import javax.inject.Inject

class GetLatestPhotosInteractor @Inject constructor(private val repository: UnsplashRepository) {

    suspend operator fun invoke(): List<Photo> {
        return repository.getLatestPhotos()
    }
}