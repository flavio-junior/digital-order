package br.com.digital.order.product.data.dto

import br.com.digital.order.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDTO(
    val id: Long = 0,
    val name: String = "",
    val categories: List<CategoryResponseDTO> ? = null,
    val price: Double = 0.0,
    val quantity: Int = 0
)
