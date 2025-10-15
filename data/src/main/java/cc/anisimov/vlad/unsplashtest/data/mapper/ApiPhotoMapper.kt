package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import javax.inject.Inject

class ApiPhotoMapper
    @Inject
    constructor(
        private val userMapper: ApiUserMapper,
        private val urlsMapper: ApiPhotoUrlsMapper,
    ) {
        fun map(photoApiModel: PhotoApiModel): PhotoDto =
            with(photoApiModel) {
                PhotoDto(
                    id = id,
                    description = description,
                    urls = urlsMapper.map(urls),
                    author = userMapper.map(user),
                )
            }

        fun map(photoApiModels: List<PhotoApiModel>): List<PhotoDto> =
            photoApiModels.map { photoApiModel ->
                map(photoApiModel)
            }
    }
