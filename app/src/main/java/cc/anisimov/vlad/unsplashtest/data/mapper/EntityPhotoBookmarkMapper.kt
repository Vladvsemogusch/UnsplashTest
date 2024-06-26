package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import javax.inject.Inject

class EntityPhotoBookmarkMapper @Inject constructor() {

    fun map(bookmarkEntity: PhotoBookmarkEntity): PhotoBookmarkDto {
        return with(bookmarkEntity) {
            PhotoBookmarkDto(
                photoId = photoId,
            )
        }
    }

    fun map(
        bookmarkEntities: List<PhotoBookmarkEntity>,
    ): List<PhotoBookmarkDto> {
        return bookmarkEntities.map { bookmarkEntity ->
            map(bookmarkEntity)
        }
    }
}