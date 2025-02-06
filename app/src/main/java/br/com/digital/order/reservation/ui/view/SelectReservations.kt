package br.com.digital.order.reservation.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.reservation.ui.viewmodel.ReservationViewModel
import br.com.digital.order.reservation.utils.ReservationsUtils.NO_RESERVATIONS_SELECTED
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Search
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.Tag
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ADD_RESERVATIONS
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectReservations(
    onDismiss: () -> Unit = {},
    onResult: (List<ReservationResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: ReservationViewModel = koinViewModel()
    var isLoading: Boolean by remember { mutableStateOf(value = false) }
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val reservations = remember { mutableStateListOf<ReservationResponseDTO>() }
    var reservationsSelected = remember { mutableStateListOf<ReservationResponseDTO>() }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    SelectObject(
        body = {
            Title(
                title = ADD_RESERVATIONS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findReservationByName(name = name)
                    isLoading = true
                }
            )
            if (isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (reservations.isNotEmpty()) {
                SelectReservations(
                    reservations = reservations,
                    reservationsSelected = reservationsSelected,
                    onResult = {
                        reservationsSelected = it.toMutableStateList()
                    }
                )
                if (reservationsSelected.isNotEmpty()) {
                    ListReservationsAvailable(reservations = reservationsSelected)
                }
            }
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = {
                    if (reservationsSelected.isNotEmpty()) {
                        viewModel.resetReservation()
                        onResult(reservationsSelected)
                        onDismiss()
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_RESERVATIONS_SELECTED)
                    }
                }
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = {
                    viewModel.resetReservation()
                    onDismiss()
                }
            )
        },
        onDismiss = {
            viewModel.resetReservation()
            onDismiss()
        }
    )
    ObserveNetworkStateHandlerFindReservationByName(
        viewModel = viewModel,
        onError = {
            observer = it
            isLoading = it.first
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = { result ->
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            isLoading = false
            reservations.clear()
            result.forEach { Reservation ->
                if (!reservations.contains(element = Reservation)) {
                    reservations.add(element = Reservation)
                }
            }
        }
    )
}

@Composable
private fun SelectReservations(
    reservations: List<ReservationResponseDTO>,
    reservationsSelected: MutableList<ReservationResponseDTO>,
    onResult: (List<ReservationResponseDTO>) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
    ) {
        items(reservations) { reservation ->
            Tag(
                text = reservation.name ?: EMPTY_TEXT,
                value = reservation,
                onCheck = { isChecked ->
                    if (isChecked) {
                        reservationsSelected.add(reservation)
                    } else {
                        reservationsSelected.remove(reservation)
                    }
                    onResult(reservationsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListReservationsAvailable(
    reservations: List<ReservationResponseDTO>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(reservations) { Reservations ->
            Description(description = Reservations.name ?: EMPTY_TEXT)
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindReservationByName(
    viewModel: ReservationViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<ReservationResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<ReservationResponseDTO>> by remember { viewModel.findReservationByName }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
