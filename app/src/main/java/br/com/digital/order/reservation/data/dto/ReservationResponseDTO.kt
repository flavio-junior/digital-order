package br.com.digital.order.reservation.data.dto

import br.com.digital.order.dashboard.domain.status.ReservationStatus
import kotlinx.serialization.Serializable

@Serializable
data class ReservationResponseDTO(
    val id: Long = 0,
    val name: String = "",
    val status: ReservationStatus? = null
)
