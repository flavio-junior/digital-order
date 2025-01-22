package br.com.digital.order.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.ui.theme.Themes

@Composable
fun PendingReservationsScreen(
    ordersResponseVO: List<OrderResponseVO>,
    onItemSelected: (OrderResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(all = Themes.size.spaceSize36),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        ordersResponseVO.forEach { orderResponseVO ->
            CardReservation(orderResponseVO = orderResponseVO)
        }
    }
}
