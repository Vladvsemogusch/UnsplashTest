package cc.anisimov.vlad.unsplashtest.data.network

import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashService {

    @Headers("Authorization: Client-ID A_EZ1V9bRfQCRXwEmDK_0gVs6nRwXIbrxc8Rd7OB3N0")
    @GET("photos")
    suspend fun getLatestPhotos(): List<PhotoApiModel>
}