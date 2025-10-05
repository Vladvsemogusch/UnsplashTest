package cc.anisimov.vlad.unsplashtest.domain.model

data class Photo(
    val id: String,
    val description: String?,
    val url: String,
    val authorName: String,
    val author: User,
    val isBookmarked: Boolean
) {

    companion object {
        val stub by lazy {
            Photo(
                id = "1",
                description = "description",
                url = "",
                authorName = "Author Name",
                author = User("1", "author_username", "", ""),
                isBookmarked = false,
            )
        }
    }
}