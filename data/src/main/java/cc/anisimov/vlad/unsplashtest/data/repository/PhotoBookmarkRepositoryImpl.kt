package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.datasource.PhotoBookmarkLocalDataSource
import cc.anisimov.vlad.unsplashtest.data.mapper.EntityPhotoBookmarkMapper
import cc.anisimov.vlad.unsplashtest.domain.di.DispatcherIO
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoBookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoBookmarkRepositoryImpl
@Inject
constructor(
    private val photoBookmarkLocalDataSource: PhotoBookmarkLocalDataSource,
    private val bookmarkMapper: EntityPhotoBookmarkMapper,
    @param:DispatcherIO private val dispatcher: CoroutineDispatcher,
) : PhotoBookmarkRepository {
    override fun getBookmarksByPhotoIdsFlow(photoIds: List<String>): Flow<List<PhotoBookmarkDto>> =
        photoBookmarkLocalDataSource
            .getBookmarksByPhotoIdsFlow(photoIds)
            .map { entityPhotoBookmarkList -> bookmarkMapper.map(entityPhotoBookmarkList) }

    override suspend fun addPhotoBookmark(photoId: String) =
        withContext(dispatcher) {
            photoBookmarkLocalDataSource.addPhotoBookmark(photoId)
        }

    override suspend fun deletePhotoBookmark(photoId: String) =
        withContext(dispatcher) {
            photoBookmarkLocalDataSource.deletePhotoBookmark(photoId)
        }
}
