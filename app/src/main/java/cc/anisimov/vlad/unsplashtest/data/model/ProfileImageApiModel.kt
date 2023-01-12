package cc.anisimov.vlad.unsplashtest.data.model


import com.google.gson.annotations.SerializedName

data class ProfileImageApiModel(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?
)