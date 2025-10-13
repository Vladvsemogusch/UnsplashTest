package cc.anisimov.vlad.unsplashtest.domain.repository

import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import kotlinx.coroutines.flow.Flow

interface PhotoBookmarkRepository {
    fun getBookmarksByPhotoIdsFlow(photoIds: List<String>): Flow<List<PhotoBookmarkDto>>

    suspend fun addPhotoBookmark(photoId: String)

    suspend fun deletePhotoBookmark(photoId: String)
}
