package cc.anisimov.vlad.unsplashtest.data.datasource

import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import javax.inject.Inject

class PhotoRemoteDataSource
@Inject
constructor(
    private val unsplashService: UnsplashService,
) {
    suspend fun getLatestPhotos(page: Int): List<PhotoApiModel> = unsplashService.getLatestPhotos(page)
}
