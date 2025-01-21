package br.com.digital.order.dashboard.data.dto

import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDTO(
    val type: TypeOrder? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    val address: AddressRequestDTO? = null,
    val objects: List<ObjectRequestDTO>,
    val payment: PaymentRequestDTO? = null
)
