package cc.anisimov.vlad.unsplashtest.feature.imagelist.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class UserUi(
    val id: String,
    val name: String,
    val bio: String?,
    val profileImageUrl: String?,
) : Parcelable {
    companion object {
        val stub by lazy {
            UserUi(
                id = "1",
                name = "John Smith",
                bio = "Bio text",
                profileImageUrl = null,
            )
        }
    }
}
