package cc.anisimov.vlad.unsplashtest.data.network.model


import com.google.gson.annotations.SerializedName

data class UrlsApiModel(
    @SerializedName("full")
    val full: String?,
    @SerializedName("raw")
    val raw: String?,
    @SerializedName("regular")
    val regular: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("thumb")
    val thumb: String?
)