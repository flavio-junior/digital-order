package br.com.digital.order.account.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
