package cc.anisimov.vlad.unsplashtest.domain.mapper

import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import javax.inject.Inject

class PhotoMapper
@Inject
constructor() {
    fun map(
        apiPhotoModels: List<PhotoDto>,
        photoBookmarkEntities: List<PhotoBookmarkDto>,
    ): List<Photo> {
        val bookmarkedPhotoIds = photoBookmarkEntities.map { it.photoId }.toHashSet()
        return apiPhotoModels.map { photo ->
            with(photo) {
                Photo(
                    id = id,
                    description = description,
                    url = urls.small,
                    authorName = authorName,
                    author = author,
                    isBookmarked = id in bookmarkedPhotoIds,
                )
            }
        }
    }
}
