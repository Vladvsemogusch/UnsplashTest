package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import javax.inject.Inject

class ApiPhotoMapper @Inject constructor(
    private val userMapper: ApiUserMapper,
    private val urlsMapper: ApiPhotoUrlsMapper
) {

    fun map(photoApiModel: PhotoApiModel): PhotoDto {
        return with(photoApiModel) {
            PhotoDto(
                id = id,
                description = description,
                urls = urlsMapper.map(urls),
                authorName = user.name,
                author = userMapper.map(user),
            )
        }
    }

    fun map(
        photoApiModels: List<PhotoApiModel>,
    ): List<PhotoDto> {
        return photoApiModels.map { photoApiModel ->
            map(photoApiModel)
        }
    }
}