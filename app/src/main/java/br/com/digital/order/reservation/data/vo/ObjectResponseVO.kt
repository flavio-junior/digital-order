package br.com.digital.order.reservation.data.vo

import br.com.digital.order.common.dto.TypeItem
import br.com.digital.order.dashboard.domain.status.ObjectStatus

data class ObjectResponseVO(
    val id: Long = 0,
    val identifier: Int = 0,
    val type: TypeItem? = null,
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val total: Double = 0.0,
    val status: ObjectStatus? = null,
    val overview: List<OverviewResponseVO>? = null
)
