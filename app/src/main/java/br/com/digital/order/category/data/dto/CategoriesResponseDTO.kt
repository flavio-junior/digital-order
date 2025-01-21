package br.com.digital.order.category.data.dto

import br.com.digital.order.common.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDTO(
    val totalPages: Int,
    val content: List<CategoryResponseDTO>,
    val pageable: PageableDTO
)
