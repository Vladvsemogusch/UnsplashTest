package cc.anisimov.vlad.unsplashtest.ui.feature.authorprofile

import androidx.compose.runtime.Immutable

@Immutable
data class AuthorProfileScreenState(
    val name: String,
    val bio: String?,
    val profileImageUrl: String?,
)
