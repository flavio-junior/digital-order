package br.com.digital.order.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.navigation.RouteApp
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.order.ui.viewmodel.ResetOrder
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.reservation.ui.view.ReservationSelected
import br.com.digital.order.reservation.ui.view.SelectReservations
import br.com.digital.order.reservation.utils.ReservationsUtils.LIST_RESERVATIONS_EMPTY
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.IsErrorMessage
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ADD_RESERVATIONS
import br.com.digital.order.utils.StringsUtils.SAVE_RESERVATIONS
import br.com.digital.order.utils.StringsUtils.SELECTED_RESERVATIONS
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncrementMoreReservationsScreen(
    orderId: Long? = null,
    goToBack: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    val viewModel: OrderViewModel = koinViewModel()
    val reservationsToSave = remember { mutableStateListOf<ReservationResponseDTO>() }
    var addNewReservation: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.add_more_reservations,
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
            OptionButton(
                icon = R.drawable.reservation,
                label = ADD_RESERVATIONS,
                onClick = {
                    addNewReservation = true
                }
            )
            Description(description = SELECTED_RESERVATIONS)
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier.horizontalScroll(state = rememberScrollState())
            ) {
                reservationsToSave.forEach { reservations ->
                    ReservationSelected(
                        reservation = reservations,
                        onItemSelected = {
                            if (reservationsToSave.contains(element = it)) {
                                reservationsToSave.remove(element = it)
                            }
                        }
                    )
                }
            }
            LoadingButton(
                label = SAVE_RESERVATIONS,
                onClick = {
                    if (reservationsToSave.isNotEmpty()) {
                        observer =
                            Triple(first = true, second = false, third = EMPTY_TEXT)
                        viewModel.incrementMoreReservationsOrder(
                            orderId = orderId ?: 0,
                            reservationsToSava = reservationsToSave.toList()
                        )
                    } else {
                        observer =
                            Triple(first = false, second = true, third = LIST_RESERVATIONS_EMPTY)
                    }
                },
                isEnabled = observer.first
            )
            IsErrorMessage(isError = observer.second, message = observer.third)
            if (addNewReservation) {
                SelectReservations(
                    onDismiss = {
                        addNewReservation = false
                    },
                    onResult = {
                        it.forEach { reservation ->
                            if (!reservationsToSave.contains(element = reservation)) {
                                reservationsToSave.add(reservation)
                            }
                        }
                        addNewReservation = false
                    }
                )
            }
        }
    }
    ObserveStateIncrementMoreReservationsOrder(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccess = {
            goToNextScreen(RouteApp.Dashboard.item)
        }
    )
}

@Composable
private fun ObserveStateIncrementMoreReservationsOrder(
    viewModel: OrderViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.incrementMoreReservationsOrder }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.INCREMENT_MORE_RESERVATIONS_ORDER)
            onSuccess()
        }
    )
}

