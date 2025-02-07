package br.com.digital.order.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.navigation.RouteApp
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.order.ui.viewmodel.ResetOrder
import br.com.digital.order.order.utils.converterReservationVOtoDTO
import br.com.digital.order.reservation.data.vo.ObjectResponseVO
import br.com.digital.order.reservation.ui.view.ReservationSelected
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.TextField
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ADD_ITEMS
import br.com.digital.order.utils.StringsUtils.ADD_RESERVATIONS
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import br.com.digital.order.utils.StringsUtils.ITEMS
import br.com.digital.order.utils.StringsUtils.RESERVATIONS
import br.com.digital.order.utils.formatterMaskToMoney
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderDetailsScreen(
    orderDetailsResponseVO: OrderResponseVO? = null,
    objectResponseVO: (Pair<ObjectResponseVO, Long>) -> Unit = {},
    goToBack: () -> Unit = {},
    goToNextScreen: (Pair<String, Long?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: OrderViewModel = koinViewModel()
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.details_order,
            goToBack = goToBack
        )
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .padding(horizontal = Themes.size.spaceSize36)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            Description(description = "$RESERVATIONS:")
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier.horizontalScroll(state = rememberScrollState())
            ) {
                orderDetailsResponseVO?.reservations?.forEach {
                    ReservationSelected(
                        reservation = converterReservationVOtoDTO(it),
                        onItemSelected = { reservation ->
                            viewModel.removeReservationOrder(
                                orderId = orderDetailsResponseVO.id,
                                reservationId = reservation.id ?: 0
                            )
                        }
                    )
                }
            }
            OptionButton(
                icon = R.drawable.add,
                label = ADD_RESERVATIONS,
                onClick = {
                    goToNextScreen(
                        Pair(
                            first = RouteApp.IncrementMoreReservations.item,
                            second = orderDetailsResponseVO?.id
                        )
                    )
                }
            )
            Description(description = "${ITEMS}:")
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier.horizontalScroll(state = rememberScrollState())
            ) {
                orderDetailsResponseVO?.objects?.forEach {
                    CardObjectResponse(
                        objectResponseVO = it,
                        onItemSelected = { result ->
                            objectResponseVO(Pair(result, orderDetailsResponseVO.id))
                        }
                    )
                }
            }
            OptionButton(
                icon = R.drawable.add,
                label = ADD_ITEMS,
                onClick = {
                    goToNextScreen(
                        Pair(
                            first = RouteApp.IncrementMoreItems.item,
                            second = orderDetailsResponseVO?.id
                        )
                    )
                }
            )
            TextField(
                enabled = false,
                label = R.string.quantity,
                value = orderDetailsResponseVO?.quantity.toString(),
                onValueChange = {},
            )
            TextField(
                enabled = false,
                label = R.string.total,
                value = formatterMaskToMoney(price = orderDetailsResponseVO?.total ?: 0.0),
                onValueChange = {}
            )
            LoadingButton(
                label = stringResource(id = R.string.cancel_order),
                onClick = {
                    openDialog = true
                },
                isEnabled = observer.first
            )
            Spacer(modifier = Modifier.height(height = Themes.size.spaceSize64))
            if (openDialog) {
                RemoveOrder(
                    onDismiss = {
                        openDialog = false
                    },
                    onItemSelected = {
                        openDialog = false
                        observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                        viewModel.deleteOrder(orderId = orderDetailsResponseVO?.id ?: 0)
                    }
                )
            }
        }
    }
    ObserveStateRemoveReservationOrder(
        viewModel = viewModel,
        goToBack = goToBack,
        goToAlternativeRoutes = goToAlternativeRoutes
    )
    ObserveStateRemoveOrder(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccess = goToBack,
        goToAlternativeRoutes = goToAlternativeRoutes
    )
}

@Composable
private fun ObserveStateRemoveReservationOrder(
    viewModel: OrderViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToBack: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.removeReservationOrder }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.REMOVE_RESERVATION)
            goToBack()
        }
    )
}

@Composable
private fun RemoveOrder(
    onDismiss: () -> Unit = {},
    onItemSelected: () -> Unit = {}
) {
    SelectObject(
        onDismiss = onDismiss,
        body = {
            Title(
                title = stringResource(id = R.string.cancel_order),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = onItemSelected
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = onDismiss
            )
        }
    )
}

@Composable
private fun ObserveStateRemoveOrder(
    viewModel: OrderViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteOrder }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.DELETE_ORDER)
            onSuccess()
        }
    )
}
