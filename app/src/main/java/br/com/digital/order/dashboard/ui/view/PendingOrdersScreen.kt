package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.order.navigation.RouteApp
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.order.ui.view.PendingReservationsScreen
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.ui.components.LoadingData
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.theme.Themes
import org.koin.androidx.compose.koinViewModel

@Composable
fun PendingOrdersScreen(
    goToNextScreen: (Pair<String, OrderResponseVO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: OrderViewModel = koinViewModel()
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllOpenOrders()
    }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ObserveNetworkStateHandlerPendingOrders(
            viewModel = viewModel,
            goToNextScreen = {
                goToNextScreen(Pair(first = RouteApp.OrderDetail.item, second = it))
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerPendingOrders(
    viewModel: OrderViewModel,
    goToNextScreen: (OrderResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<OrdersResponseVO> by remember { viewModel.findAllOpenOrders }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            it.result?.let { ordersResult ->
                PendingReservationsScreen(
                    ordersResponseVO = ordersResult,
                    goToNextScreen = goToNextScreen
                )
            }
        }
    )
}
