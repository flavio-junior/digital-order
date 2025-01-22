package br.com.digital.order.order.data.vo

import br.com.digital.order.common.vo.PageableVO

data class OrdersResponseVO(
    val totalPages: Int,
    val content: List<OrderResponseVO>? = null,
    val pageable: PageableVO
)
