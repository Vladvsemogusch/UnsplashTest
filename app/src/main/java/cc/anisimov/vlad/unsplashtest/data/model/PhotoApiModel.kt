package cc.anisimov.vlad.unsplashtest.data.model


import com.google.gson.annotations.SerializedName

data class PhotoApiModel(
    @SerializedName("blur_hash")
    val blurHash: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("urls")
    val urls: UrlsApiModel?,
    @SerializedName("user")
    val user: UserApiModel?,
    @SerializedName("width")
    val width: Int?
)