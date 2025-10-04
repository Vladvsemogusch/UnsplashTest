package cc.anisimov.vlad.unsplashtest.di

import android.content.Context
import cc.anisimov.vlad.unsplashtest.data.db.AppDatabase
import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import cc.anisimov.vlad.unsplashtest.data.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideUnsplashService(retrofit: Retrofit): UnsplashService {
        return retrofit.create(UnsplashService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesJsonConverterFactory(json: Json): Converter.Factory = json.asConverterFactory(
        MEDIA_TYPE_JSON.toMediaType()
    )

    @Singleton
    @Provides
    fun providesJson() = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.createInstance(context)
    }

    @Singleton
    @Provides
    fun providesBookmarkDao(appDatabase: AppDatabase) = appDatabase.getPhotoBookmarkDao()

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"
        private const val MEDIA_TYPE_JSON = "application/json; charset=UTF8"
    }
}