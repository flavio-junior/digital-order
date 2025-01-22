package br.com.digital.order.order.ui.view

import br.com.digital.order.utils.StringsUtils.DELIVERY
import br.com.digital.order.utils.StringsUtils.PICKUP
import br.com.digital.order.utils.StringsUtils.RESERVATIONS

enum class ItemsOrder(val text: String) {
    PendingOrders(text = DELIVERY),
    DetailsOrder(text = PICKUP),
    DetailsItem(text = RESERVATIONS)
}
