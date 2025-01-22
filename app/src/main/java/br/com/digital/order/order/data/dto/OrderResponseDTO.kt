package br.com.digital.order.order.data.dto

import br.com.digital.order.dashboard.domain.status.OrderStatus
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDTO(
    val id: Long = 0,
    @SerialName(value = "created_at")
    val createdAt: String = "",
    @SerialName(value = "qr_code")
    var qrCode: String? = "",
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseDTO>? = null,
    val address: AddressResponseDTO? = null,
    val objects: List<ObjectResponseDTO>? = null,
    val quantity: Int = 0,
    val total: Double = 0.0,
    val payment: PaymentResponseDTO? = null
)
