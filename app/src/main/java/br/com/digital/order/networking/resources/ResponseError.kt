package br.com.digital.order.networking.resources

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val status: Int,
    val message: String
)
