package cc.anisimov.vlad.unsplashtest.domain.model.dto

data class PhotoUrlsDto(
    val regular: String,
    val small: String,
) {
    companion object {
        val stub
            get() =
                PhotoUrlsDto(
                    regular = "https://example.com/img-regular.jpg",
                    small = "https://example.com/img-small.jpg",
                )
    }
}
