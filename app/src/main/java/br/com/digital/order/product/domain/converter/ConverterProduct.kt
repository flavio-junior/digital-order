package br.com.digital.order.product.domain.converter

import br.com.digital.order.category.domain.ConverterCategory
import br.com.digital.order.product.data.dto.ProductResponseDTO
import br.com.digital.order.product.data.vo.ProductResponseVO


class ConverterProduct(
    private val converter: ConverterCategory
) {

    private fun converterProductsResponseDTOToVO(
        products: List<ProductResponseDTO>
    ): List<ProductResponseVO> {
        return products.map {
            ProductResponseVO(
                id = it.id,
                name = it.name,
                categories = converter.converterCategoriesResponseDTOToVO(
                    categories = it.categories ?: emptyList()
                ),
                price = it.price,
                quantity = it.quantity
            )
        }
    }
}
