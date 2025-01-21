package br.com.digital.order.dashboard.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.account.data.repository.local.LocalStorage
import br.com.digital.order.dashboard.data.dto.OrderRequestDTO
import br.com.digital.order.dashboard.data.repository.DashboardRepository
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

interface UiSharedImpl {
    fun logoutApplication()
}

class DashboardViewModel(
    private val localStorage: LocalStorage,
    private val repository: DashboardRepository
) : ViewModel(), UiSharedImpl {

    private val _createOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val createOrder: State<ObserveNetworkStateHandler<Unit>> = _createOrder

    private val _logoutApplication =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val logoutApplication: State<ObserveNetworkStateHandler<Unit>> = _logoutApplication

    fun createOrder(order: OrderRequestDTO) {
        viewModelScope.launch {
            repository.createNewOrder(order = order)
                .onStart {
                    _createOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createOrder.value = it
                }
        }
    }

    override fun logoutApplication() {
        viewModelScope.launch {
            _logoutApplication.value = ObserveNetworkStateHandler.Loading(l = true)
            localStorage.cleanToken()
            _logoutApplication.value = ObserveNetworkStateHandler.Success(s = Unit)
        }
    }

    fun resetOrder() {
        _createOrder.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
