package cc.anisimov.vlad.unsplashtest.domain.model

data class User(
    val id: String,
    val name: String,
    val bio: String?,
    val profileImageUrl: String?,
) {
    companion object {
        val stub
            get() =
                User(
                    id = "u1",
                    name = "John Smith",
                    bio = "Bio text",
                    profileImageUrl = "https://example.com/profile.jpg",
                )
    }
}
