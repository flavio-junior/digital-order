package br.com.digital.order.reservation.data.vo

import br.com.digital.order.dashboard.domain.status.AddressStatus

data class AddressResponseVO(
    val id: Long? = 0,
    val status: AddressStatus? = null,
    val complement: String? = "",
    val district: String? = "",
    val number: Int? = 0,
    val street: String? = ""
)
