package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.network.model.PhotoUrlsApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoUrlsDto
import javax.inject.Inject

class ApiPhotoUrlsMapper
    @Inject
    constructor() {
        fun map(apiPhotoUrls: PhotoUrlsApiModel): PhotoUrlsDto =
            PhotoUrlsDto(
                regular = apiPhotoUrls.regular,
                small = apiPhotoUrls.small,
            )
    }
