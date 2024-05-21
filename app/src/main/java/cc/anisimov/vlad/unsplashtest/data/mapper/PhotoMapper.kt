package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import javax.inject.Inject

class PhotoMapper @Inject constructor() {

    fun map(photoApiModel: PhotoApiModel, photoBookmark: PhotoBookmarkEntity?): Photo {
        return with(photoApiModel) {
            Photo(
                id = id ?: throw IllegalArgumentException("Photo id is null"),
                description = description,
                url = urls?.regular ?: throw IllegalArgumentException("Photo url is null"),
                authorName = user?.name
                    ?: throw IllegalArgumentException("User or user name is null"),
                isBookmarked = photoBookmark != null
            )
        }
    }

    fun map(
        photoApiModels: List<PhotoApiModel>,
        photoBookmarks: List<PhotoBookmarkEntity?>
    ): List<Photo> {
        return photoApiModels.map { photoApiModel ->
            val photoBookmark = photoBookmarks.firstOrNull { it?.photoId == photoApiModel.id }
            map(photoApiModel, photoBookmark)
        }
    }
}