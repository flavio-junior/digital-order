package br.com.digital.order.order.data.dto

import br.com.digital.order.common.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrdersResponseDTO(
    val totalPages: Int,
    val content: List<OrderResponseDTO>,
    val pageable: PageableDTO
)
