package br.com.digital.order.reservation.data.vo

import br.com.digital.order.dashboard.domain.status.ObjectStatus

data class OverviewResponseVO(
    var id: Long = 0,
    var createdAt: String? = "",
    var status: ObjectStatus? = null,
    var quantity: Int = 0
)
