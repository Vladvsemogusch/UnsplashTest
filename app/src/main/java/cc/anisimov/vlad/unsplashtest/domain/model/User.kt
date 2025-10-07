package cc.anisimov.vlad.unsplashtest.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class User(
    val id: String,
    val name: String,
    val bio: String?,
    val profileImageUrl: String?,
) : Parcelable {
    companion object {
        val stub by lazy {
            User(
                id = "1",
                name = "John Smith",
                bio = "Bio text",
                profileImageUrl = null,
            )
        }
    }
}
