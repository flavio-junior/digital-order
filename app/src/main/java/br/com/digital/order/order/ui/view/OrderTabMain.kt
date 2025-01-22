package br.com.digital.order.order.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.utils.NumbersUtils

@Composable
fun OrderTabMain(
    actualStep: Int,
    goToNextScreen: (Pair<String, Long?>) -> Unit = {},
    ordersResponseVO: List<OrderResponseVO>
) {
    val tabIndex by remember { mutableIntStateOf(value = actualStep) }
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
    when (tabIndex) {
        NumbersUtils.NUMBER_ZERO -> PendingDeliveryScreen(
            ordersResponseVO = delivery,
            goToNextScreen = goToNextScreen
        )

        NumbersUtils.NUMBER_ONE -> PendingPickupScreen(
            ordersResponseVO = ordersResponseVO,
            goToNextScreen = goToNextScreen
        )

        NumbersUtils.NUMBER_TWO -> PendingReservationsScreen(
            ordersResponseVO = reservation,
            goToNextScreen = {  }
        )
    }
}
