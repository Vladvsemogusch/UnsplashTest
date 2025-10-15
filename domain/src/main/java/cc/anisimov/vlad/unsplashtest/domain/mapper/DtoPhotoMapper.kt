package cc.anisimov.vlad.unsplashtest.domain.mapper

import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import javax.inject.Inject

class DtoPhotoMapper
@Inject
constructor() {
    fun map(
        dtoPhotoModels: List<PhotoDto>,
        photoBookmarkEntities: List<PhotoBookmarkDto>,
    ): List<Photo> {
        val bookmarkedPhotoIds = photoBookmarkEntities.map { it.photoId }.toHashSet()
        return dtoPhotoModels.map { photo ->
            with(photo) {
                Photo(
                    id = id,
                    description = description,
                    url = urls.small,
                    author = author,
                    isBookmarked = id in bookmarkedPhotoIds,
                )
            }
        }
    }
}
