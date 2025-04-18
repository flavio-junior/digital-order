package br.com.digital.order.dashboard.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddressRequestDTO(
    val complement: String = "",
    val district: String = "",
    val number: Int = 0,
    val street: String = ""
)
