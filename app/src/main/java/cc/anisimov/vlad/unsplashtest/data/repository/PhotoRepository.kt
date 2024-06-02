package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.db.AppDatabase
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import cc.anisimov.vlad.unsplashtest.data.mapper.PhotoMapper
import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService,
    private val database: AppDatabase,
    private val photoMapper: PhotoMapper
) {

    suspend fun getLatestPhotos(page: Int): List<Photo> {
        val latestPhotos = unsplashService.getLatestPhotos(page)
        val photoBookmarks = database.getPhotoBookmarkDao().getAllBookmarks()
        return photoMapper.map(latestPhotos, photoBookmarks)
    }

    suspend fun addPhotoBookmark(photoId: String) {
        val photoBookmark = PhotoBookmarkEntity(photoId)
        database.getPhotoBookmarkDao().insertBookmark(photoBookmark)
    }

    suspend fun deletePhotoBookmark(photoId: String) {
        val photoBookmark = PhotoBookmarkEntity(photoId)
        database.getPhotoBookmarkDao().deleteBookmark(photoBookmark)
    }
}