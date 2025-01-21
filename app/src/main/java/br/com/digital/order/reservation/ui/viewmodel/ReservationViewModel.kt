package br.com.digital.order.reservation.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.reservation.data.repository.ReservationRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val repository: ReservationRepository
): ViewModel() {

    private val _findReservationByName =
        mutableStateOf<ObserveNetworkStateHandler<List<ReservationResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findReservationByName: State<ObserveNetworkStateHandler<List<ReservationResponseDTO>>> =
        _findReservationByName

    fun findReservationByName(name: String) {
        viewModelScope.launch {
            repository.finReservationByName(name = name)
                .onStart {
                    _findReservationByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findReservationByName.value = it
                }
        }
    }

    fun resetReservation() {
        _findReservationByName.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
