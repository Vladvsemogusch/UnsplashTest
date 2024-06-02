package cc.anisimov.vlad.unsplashtest.data.network

import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashService {

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("photos")
    suspend fun getLatestPhotos(@Query("page") page: Int): List<PhotoApiModel>

    companion object {
        const val CLIENT_ID = "A_EZ1V9bRfQCRXwEmDK_0gVs6nRwXIbrxc8Rd7OB3N0"
    }
}