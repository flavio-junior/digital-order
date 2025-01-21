package br.com.digital.order.account.data.dto

import br.com.digital.order.account.domain.type.TypeAccount
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDTO(
    @SerializedName("user")
    val user: String,
    @SerializedName("authenticated")
    val authenticated: Boolean,
    @SerializedName("created")
    val created: String,
    @SerializedName("type")
    val type: TypeAccount,
    @SerializedName("expiration")
    val expiration: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)
