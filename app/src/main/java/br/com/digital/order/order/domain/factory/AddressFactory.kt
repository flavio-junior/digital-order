package br.com.digital.order.order.domain.factory

import br.com.digital.order.dashboard.domain.status.AddressStatus
import br.com.digital.order.utils.StringsUtils.DELIVERED
import br.com.digital.order.utils.StringsUtils.EMPTY_STATUS
import br.com.digital.order.utils.StringsUtils.PENDING_DELIVERY
import br.com.digital.order.utils.StringsUtils.SENDING

fun addressFactory(status: AddressStatus? = null): String {
    return when (status) {
        AddressStatus.PENDING_DELIVERY -> PENDING_DELIVERY
        AddressStatus.SENDING -> SENDING
        AddressStatus.DELIVERED -> DELIVERED
        null -> EMPTY_STATUS
    }
}
