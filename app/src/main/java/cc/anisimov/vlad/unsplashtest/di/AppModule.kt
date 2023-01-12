package cc.anisimov.vlad.unsplashtest.di

import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideUnsplashService(): UnsplashService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(UnsplashService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"
    }
}