package cc.anisimov.vlad.unsplashtest.domain.model.dto

import cc.anisimov.vlad.unsplashtest.domain.model.User

data class PhotoDto(
    val id: String,
    val description: String?,
    val urls: PhotoUrlsDto,
    val author: User,
) {
    companion object {
        val stub
            get() =
                PhotoDto(
                    id = "d1",
                    description = "Description",
                    urls = PhotoUrlsDto.stub,
                    author = User.stub,
                )
    }
}
