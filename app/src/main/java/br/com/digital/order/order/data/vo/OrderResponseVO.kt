package br.com.digital.order.order.data.vo

import br.com.digital.order.dashboard.domain.status.OrderStatus
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.reservation.data.vo.AddressResponseVO
import br.com.digital.order.reservation.data.vo.ObjectResponseVO
import br.com.digital.order.reservation.data.vo.ReservationResponseVO
import br.com.digital.order.reservation.data.vo.PaymentResponseVO

data class OrderResponseVO(
    val id: Long = 0,
    val createdAt: String = "",
    var qrCode: String? = "",
    val type: TypeOrder? = null,
    val status: OrderStatus? = null,
    val reservations: List<ReservationResponseVO>? = null,
    val address: AddressResponseVO? = null,
    val objects: List<ObjectResponseVO>? = null,
    val quantity: Int = 0,
    val total: Double = 0.0,
    val payment: PaymentResponseVO? = null
)
