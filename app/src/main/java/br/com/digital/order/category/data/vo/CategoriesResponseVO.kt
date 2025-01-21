package br.com.digital.order.category.data.vo

import br.com.digital.order.common.vo.PageableVO

data class CategoriesResponseVO(
    val totalPages: Int,
    val content: List<CategoryResponseVO>,
    val pageable: PageableVO
)
