package br.com.digital.order.category.domain

import br.com.digital.order.common.domain.converterPageableDTOToVO
import br.com.digital.order.category.data.dto.CategoriesResponseDTO
import br.com.digital.order.category.data.dto.CategoryResponseDTO
import br.com.digital.order.category.data.vo.CategoriesResponseVO
import br.com.digital.order.category.data.vo.CategoryResponseVO

class ConverterCategory {

    fun converterContentDTOToVO(content: CategoriesResponseDTO): CategoriesResponseVO {
        return CategoriesResponseVO(
            totalPages = content.totalPages,
            content = converterCategoriesResponseDTOToVO(categories = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    fun converterCategoriesResponseDTOToVO(
        categories: List<CategoryResponseDTO>
    ): List<CategoryResponseVO> {
        return categories.map {
            CategoryResponseVO(
                id = it.id,
                name = it.name
            )
        }
    }
}
