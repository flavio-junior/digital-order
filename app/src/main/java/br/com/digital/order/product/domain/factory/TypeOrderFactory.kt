package br.com.digital.order.product.domain.factory

import br.com.digital.order.common.dto.TypeItem
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils

fun typeOrderFactory(type: TypeItem? = null): String {
    return when (type) {
        TypeItem.FOOD -> StringsUtils.FOOD
        TypeItem.PRODUCT -> StringsUtils.PRODUCT
        TypeItem.ITEM -> StringsUtils.ITEM
        else -> EMPTY_TEXT
    }
}
