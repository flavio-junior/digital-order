package br.com.digital.order.reservation.data.vo

import br.com.digital.order.dashboard.domain.status.ReservationStatus

data class ReservationResponseVO(
    val id: Long? = 0,
    val name: String? = "",
    val status: ReservationStatus? = null
)
