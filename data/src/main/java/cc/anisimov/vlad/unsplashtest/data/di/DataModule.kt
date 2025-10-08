package cc.anisimov.vlad.unsplashtest.data.di

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoBookmarkRepositoryImpl
import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepositoryImpl
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoBookmarkRepository
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    @Singleton
    abstract fun bindPhotoBookmarkRepository(photoBookmarkRepositoryImpl: PhotoBookmarkRepositoryImpl): PhotoBookmarkRepository
}
