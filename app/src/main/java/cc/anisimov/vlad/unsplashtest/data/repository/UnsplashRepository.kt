package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.mapper.PhotoApiModelMapper
import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val unsplashService: UnsplashService,
    private val photoApiModelMapper: PhotoApiModelMapper
) {

    suspend fun getLatestPhotos(): List<Photo> {
        val latestPhotosApiModel = unsplashService.getLatestPhotos()
        return photoApiModelMapper.map(latestPhotosApiModel)
    }
}