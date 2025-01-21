package br.com.digital.order.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageableDTO(
    val pageNumber: Int
)
