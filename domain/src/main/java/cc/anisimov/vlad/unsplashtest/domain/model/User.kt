package cc.anisimov.vlad.unsplashtest.domain.model

data class User(
    val id: String,
    val name: String,
    val bio: String?,
    val profileImageUrl: String?,
)
