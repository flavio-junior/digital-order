package br.com.digital.order.order.domain.factory

import br.com.digital.order.R
import br.com.digital.order.dashboard.domain.status.AddressStatus
import br.com.digital.order.dashboard.domain.status.ObjectStatus
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.utils.StringsUtils.DELIVERED
import br.com.digital.order.utils.StringsUtils.EMPTY_STATUS
import br.com.digital.order.utils.StringsUtils.PENDING_DELIVERY
import br.com.digital.order.utils.StringsUtils.SENDING

fun typeOrderFactory(type: TypeOrder? = null): Int? {
    return when (type) {
        TypeOrder.RESERVATION -> R.string.reservation
        TypeOrder.ORDER -> R.string.order
        else -> null
    }
}

fun statusObject(status: ObjectStatus? = null): String {
   return when (status) {
        ObjectStatus.PENDING -> PENDING_DELIVERY
        ObjectStatus.DELIVERED -> DELIVERED
        null -> EMPTY_STATUS
    }
}

fun addressFactory(status: AddressStatus? = null): String {
    return when (status) {
        AddressStatus.PENDING_DELIVERY -> PENDING_DELIVERY
        AddressStatus.SENDING -> SENDING
        AddressStatus.DELIVERED -> DELIVERED
        null -> EMPTY_STATUS
    }
}
