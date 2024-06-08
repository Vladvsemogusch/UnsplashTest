package cc.anisimov.vlad.unsplashtest.domain.model.dto

import cc.anisimov.vlad.unsplashtest.domain.model.User

data class PhotoDto(
    val id: String,
    val description: String?,
    val urls: PhotoUrlsDto,
    val authorName: String,
    val author: User,
)
