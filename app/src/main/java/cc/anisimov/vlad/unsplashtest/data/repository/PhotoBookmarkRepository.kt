package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.datasource.PhotoBookmarkLocalDataSource
import cc.anisimov.vlad.unsplashtest.data.mapper.EntityPhotoBookmarkMapper
import cc.anisimov.vlad.unsplashtest.di.DispatcherIO
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoBookmarkRepository @Inject constructor(
    private val photoBookmarkLocalDataSource: PhotoBookmarkLocalDataSource,
    private val bookmarkMapper: EntityPhotoBookmarkMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAllBookmarks(): List<PhotoBookmarkDto> = withContext(dispatcher) {
        val entityPhotoBookmarks = photoBookmarkLocalDataSource.getAllBookmarks()
        bookmarkMapper.map(entityPhotoBookmarks)
    }

    suspend fun addPhotoBookmark(photoId: String) = withContext(dispatcher) {
        photoBookmarkLocalDataSource.addPhotoBookmark(photoId)
    }

    suspend fun deletePhotoBookmark(photoId: String) = withContext(dispatcher) {
        photoBookmarkLocalDataSource.deletePhotoBookmark(photoId)
    }
}