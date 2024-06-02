package cc.anisimov.vlad.unsplashtest.di

import android.content.Context
import cc.anisimov.vlad.unsplashtest.data.db.AppDatabase
import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideUnsplashService(gsonConverterFactory: GsonConverterFactory): UnsplashService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(UnsplashService::class.java)
    }

    @Singleton
    @Provides
    fun provideGsonConvertFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.createInstance(context)
    }

    @Singleton
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO


    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"
    }
}