package cc.anisimov.vlad.unsplashtest.domain.model

data class Photo(
    val id: String,
    val description: String?,
    val url: String,
    val authorName: String,
    val author: User,
    val isBookmarked: Boolean,
)
