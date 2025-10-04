package cc.anisimov.vlad.unsplashtest.data.datasource

import cc.anisimov.vlad.unsplashtest.data.db.dao.PhotoBookmarkDao
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import javax.inject.Inject

class PhotoBookmarkLocalDataSource @Inject constructor(private val photoBookmarkDao: PhotoBookmarkDao) {

    suspend fun getAllBookmarks(): List<PhotoBookmarkEntity> =
        photoBookmarkDao.getAllBookmarks()

    suspend fun addPhotoBookmark(photoId: String) {
        val photoBookmark = PhotoBookmarkEntity(photoId)
        photoBookmarkDao.insertBookmark(photoBookmark)
    }

    suspend fun deletePhotoBookmark(photoId: String) {
        val photoBookmark = PhotoBookmarkEntity(photoId)
        photoBookmarkDao.deleteBookmark(photoBookmark)
    }
}