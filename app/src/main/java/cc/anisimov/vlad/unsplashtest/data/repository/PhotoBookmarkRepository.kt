package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.datasource.PhotoBookmarkLocalDataSource
import cc.anisimov.vlad.unsplashtest.data.mapper.EntityPhotoBookmarkMapper
import cc.anisimov.vlad.unsplashtest.di.DispatcherIO
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoBookmarkRepository
    @Inject
    constructor(
        private val photoBookmarkLocalDataSource: PhotoBookmarkLocalDataSource,
        private val bookmarkMapper: EntityPhotoBookmarkMapper,
        @param:DispatcherIO private val dispatcher: CoroutineDispatcher,
    ) {
        fun getAllBookmarks(): Flow<List<PhotoBookmarkDto>> =
            photoBookmarkLocalDataSource
                .getAllBookmarks()
                .map { entityPhotoBookmarkList -> bookmarkMapper.map(entityPhotoBookmarkList) }

        suspend fun addPhotoBookmark(photoId: String) =
            withContext(dispatcher) {
                photoBookmarkLocalDataSource.addPhotoBookmark(photoId)
            }

        suspend fun deletePhotoBookmark(photoId: String) =
            withContext(dispatcher) {
                photoBookmarkLocalDataSource.deletePhotoBookmark(photoId)
            }
    }
