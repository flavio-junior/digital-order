package br.com.digital.order.order.data.dto

import br.com.digital.order.dashboard.domain.status.AddressStatus
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponseDTO(
    val id: Long? = 0,
    val status: AddressStatus? = null,
    val complement: String? = "",
    val district: String? = "",
    val number: Int? = 0,
    val street: String? = ""
)
