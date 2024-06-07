package cc.anisimov.vlad.unsplashtest.data.network

import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {

    @GET("photos")
    suspend fun getLatestPhotos(@Query("page") page: Int): List<PhotoApiModel>
}