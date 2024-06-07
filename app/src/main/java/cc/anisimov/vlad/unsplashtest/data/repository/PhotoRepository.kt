package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.db.dao.PhotoBookmarkDao
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import cc.anisimov.vlad.unsplashtest.data.mapper.PhotoMapper
import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService,
    private val bookmarkDao: PhotoBookmarkDao,
    private val photoMapper: PhotoMapper
) {

    suspend fun getLatestPhotos(page: Int): List<Photo> {
        var latestPhotos = unsplashService.getLatestPhotos(page)
        latestPhotos = applyApiFix(page, latestPhotos)
        val photoBookmarks = bookmarkDao.getAllBookmarks()
        return photoMapper.map(latestPhotos, photoBookmarks)
    }

    // Api paging is bugged as of 2.06.24. Returns duplicate photos starting from page 2 (first 2 items)
    private fun applyApiFix(page: Int, latestPhotos: List<PhotoApiModel>): List<PhotoApiModel> {
        return if (page > 1) {
            latestPhotos.subList(2, latestPhotos.size)
        } else {
            latestPhotos
        }
    }

    suspend fun addPhotoBookmark(photoId: String) {
        val photoBookmark = PhotoBookmarkEntity(photoId)
        bookmarkDao.insertBookmark(photoBookmark)
    }

    suspend fun deletePhotoBookmark(photoId: String) {
        val photoBookmark = PhotoBookmarkEntity(photoId)
        bookmarkDao.deleteBookmark(photoBookmark)
    }
}