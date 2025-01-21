package br.com.digital.order.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class ObjectRequestDTO(
    val name: String,
    val identifier: Long,
    var quantity: Int,
    val type: TypeItem
)
