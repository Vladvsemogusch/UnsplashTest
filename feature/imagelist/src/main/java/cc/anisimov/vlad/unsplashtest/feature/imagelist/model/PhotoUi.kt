package cc.anisimov.vlad.unsplashtest.feature.imagelist.model

import androidx.compose.runtime.Immutable

@Immutable
data class PhotoUi(
    val id: String,
    val description: String?,
    val url: String,
    val authorName: String,
    val author: UserUi,
    val isBookmarked: Boolean,
) {
    companion object {
        val stub by lazy {
            PhotoUi(
                id = "1",
                description = "description",
                url = "",
                authorName = "Author Name",
                author = UserUi("1", "author_username", "", ""),
                isBookmarked = false,
            )
        }
    }
}
