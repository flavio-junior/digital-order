package br.com.digital.order.dashboard.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.account.data.repository.local.LocalStorage
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.launch

interface UiSharedImpl {
    fun logoutApplication()
}

class DashboardViewModel(
    private val localStorage: LocalStorage
): ViewModel(), UiSharedImpl {

    private val _logoutApplication =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val logoutApplication: State<ObserveNetworkStateHandler<Unit>> = _logoutApplication

    override fun logoutApplication() {
        viewModelScope.launch {
            _logoutApplication.value = ObserveNetworkStateHandler.Loading(l = true)
            localStorage.cleanToken()
            _logoutApplication.value = ObserveNetworkStateHandler.Success(s = Unit)
        }
    }
}
