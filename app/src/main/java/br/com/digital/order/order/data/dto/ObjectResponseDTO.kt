package br.com.digital.order.order.data.dto

import br.com.digital.order.common.dto.TypeItem
import br.com.digital.order.dashboard.domain.status.ObjectStatus
import kotlinx.serialization.Serializable

@Serializable
data class ObjectResponseDTO(
    val id: Long,
    val identifier: Int,
    val type: TypeItem,
    val name: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val status: ObjectStatus? = null,
    val overview: List<OverviewResponseDTO>? = null
)
