package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.model.PhotoApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import javax.inject.Inject

class PhotoApiModelMapper @Inject constructor() {

    fun map(from: PhotoApiModel): Photo {
        return with(from) {
            Photo(
                id = id ?: throw IllegalArgumentException("Photo id is null"),
                description = description,
                url = urls?.regular ?: throw IllegalArgumentException("Photo url is null")
            )
        }
    }

    fun map(from: List<PhotoApiModel>): List<Photo> {
        return from.map { map(it) }
    }
}