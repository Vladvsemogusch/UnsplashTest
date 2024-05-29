package cc.anisimov.vlad.unsplashtest.data.network.model


import com.google.gson.annotations.SerializedName

data class UserApiModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    @SerializedName("profile_image")
    val profileImage: ProfileImageApiModel,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    @SerializedName("username")
    val username: String
)