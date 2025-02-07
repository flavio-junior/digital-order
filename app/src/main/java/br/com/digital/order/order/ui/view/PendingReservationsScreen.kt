package br.com.digital.order.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.ui.components.SubTitle
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes

@Composable
fun PendingReservationsScreen(
    ordersResponseVO: OrdersResponseVO,
    goToNextScreen: (OrderResponseVO) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(all = Themes.size.spaceSize36),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        SubTitle(
            subTitle = stringResource(id = R.string.pending_reservations),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        ordersResponseVO.content?.forEach { orderResponseVO ->
            when (orderResponseVO.type) {
                TypeOrder.RESERVATION -> {
                    CardReservation(
                        orderResponseVO = orderResponseVO,
                        onItemSelected = goToNextScreen
                    )
                }

                else -> {

                }
            }
        }
    }
}
