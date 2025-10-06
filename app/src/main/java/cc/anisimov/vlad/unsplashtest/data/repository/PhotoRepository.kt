package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.datasource.PhotoRemoteDataSource
import cc.anisimov.vlad.unsplashtest.data.mapper.ApiPhotoMapper
import cc.anisimov.vlad.unsplashtest.di.DispatcherIO
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepository
    @Inject
    constructor(
        private val photoRemoteDataSource: PhotoRemoteDataSource,
        private val photoMapper: ApiPhotoMapper,
        @param:DispatcherIO private val dispatcher: CoroutineDispatcher,
    ) {
        suspend fun getLatestPhotos(page: Int): List<PhotoDto> =
            withContext(dispatcher) {
                val apiPhotoModels = photoRemoteDataSource.getLatestPhotos(page)
                photoMapper.map(apiPhotoModels)
            }
    }
