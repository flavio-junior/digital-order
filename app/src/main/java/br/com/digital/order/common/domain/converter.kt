package br.com.digital.order.common.domain

import br.com.digital.order.common.dto.PageableDTO
import br.com.digital.order.common.vo.PageableVO

fun converterPageableDTOToVO(pageable: PageableDTO): PageableVO {
    return PageableVO(
        pageNumber = pageable.pageNumber
    )
}
