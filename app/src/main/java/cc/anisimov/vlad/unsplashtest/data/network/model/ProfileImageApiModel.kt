package cc.anisimov.vlad.unsplashtest.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageApiModel(
    @SerialName("large")
    val large: String?,
    @SerialName("medium")
    val medium: String?,
    @SerialName("small")
    val small: String?
)