package cc.anisimov.vlad.unsplashtest.feature.imagelist.mapper

import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.PhotoUi
import javax.inject.Inject

class PhotoMapper
@Inject
constructor(
    val userMapper: UserMapper,
) {
    fun map(photo: Photo): PhotoUi =
        PhotoUi(
            id = photo.id,
            description = photo.description,
            url = photo.url,
            authorName = photo.authorName,
            author = userMapper.map(photo.author),
            isBookmarked = photo.isBookmarked,
        )

    fun map(photos: List<Photo>): List<PhotoUi> = photos.map { photo -> map(photo) }
}
