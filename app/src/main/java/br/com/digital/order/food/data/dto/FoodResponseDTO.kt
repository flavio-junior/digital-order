package br.com.digital.order.food.data.dto

import br.com.digital.order.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class FoodResponseDTO(
    val id: Long,
    val name: String,
    val categories: List<CategoryResponseDTO>? = null,
    val price: Double
)
