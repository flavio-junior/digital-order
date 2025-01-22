package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.order.ui.view.OrdersTabs
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.ui.components.LoadingData
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.theme.Themes
import org.koin.androidx.compose.koinViewModel

@Composable
fun PendingOrdersScreen(
    actualStep: Int,
    goToNextScreen: (Pair<String, Long?>) -> Unit = {},
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
            actualStep = actualStep,
            viewModel = viewModel,
            goToNextScreen = goToNextScreen,
            goToAlternativeRoutes = goToAlternativeRoutes,
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerPendingOrders(
    actualStep: Int,
    viewModel: OrderViewModel,
    goToNextScreen: (Pair<String, Long?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
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
                OrdersTabs(
                    actualStep = actualStep,
                    ordersResponseVO = ordersResult,
                    goToNextScreen = goToNextScreen
                )
            }
        }
    )
}
