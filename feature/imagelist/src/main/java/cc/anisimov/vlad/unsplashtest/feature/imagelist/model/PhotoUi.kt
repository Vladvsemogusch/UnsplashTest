package cc.anisimov.vlad.unsplashtest.feature.imagelist.model

import androidx.compose.runtime.Immutable

@Immutable
data class PhotoUi(
    val id: String,
    val description: String?,
    val url: String,
    val author: UserUi,
    val isBookmarked: Boolean,
) {
    companion object {
        val stub
            get() =
                PhotoUi(
                    id = "1",
                    description = "description",
                    url = "",
                    author = UserUi.stub,
                    isBookmarked = false,
                )
    }
}
