package br.com.digital.order.order.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.utils.NumbersUtils

@Composable
fun OrderTabMain(
    index: Int,
    ordersResponseVO: List<OrderResponseVO>
) {
    val delivery = mutableListOf<OrderResponseVO>()
    val order = mutableListOf<OrderResponseVO>()
    val reservation = mutableListOf<OrderResponseVO>()
    ordersResponseVO.forEach {
        when (it.type) {
            TypeOrder.DELIVERY -> {
                delivery.add(element = it)
            }

            TypeOrder.ORDER -> {
                order.add(element = it)
            }

            TypeOrder.RESERVATION -> {
                reservation.add(element = it)
            }

            else -> {}
        }
    }
    when (index) {
        NumbersUtils.NUMBER_ZERO -> PendingDeliveryScreen(ordersResponseVO = delivery)

        NumbersUtils.NUMBER_ONE -> PendingPickupScreen(ordersResponseVO= ordersResponseVO)

        NumbersUtils.NUMBER_TWO -> PendingReservationsScreen(ordersResponseVO = reservation)
    }
}
