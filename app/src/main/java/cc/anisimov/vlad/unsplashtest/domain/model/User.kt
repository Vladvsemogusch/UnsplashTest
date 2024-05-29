package cc.anisimov.vlad.unsplashtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val bio: String?,
    val profileImageUrl: String?
) : Parcelable