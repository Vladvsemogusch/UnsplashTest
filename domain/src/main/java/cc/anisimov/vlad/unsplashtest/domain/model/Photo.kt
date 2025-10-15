package cc.anisimov.vlad.unsplashtest.domain.model

data class Photo(
    val id: String,
    val description: String?,
    val url: String,
    val author: User,
    val isBookmarked: Boolean,
) {
    companion object {
        val stub
            get() =
                Photo(
                    id = "p1",
                    description = "Sample description",
                    url = "https://example.com/image.jpg",
                    author = User.stub,
                    isBookmarked = false,
                )
    }
}
