package br.com.digital.order.item.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.item.data.dto.ItemResponseDTO
import br.com.digital.order.item.data.repository.ItemRepository
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ItemViewModel(
    private val repository: ItemRepository
): ViewModel() {

    private val _findItemByName =
        mutableStateOf<ObserveNetworkStateHandler<List<ItemResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findItemByName: State<ObserveNetworkStateHandler<List<ItemResponseDTO>>> =
        _findItemByName

    fun findItemByName(name: String) {
        viewModelScope.launch {
            repository.finItemByName(name = name)
                .onStart {
                    _findItemByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findItemByName.value = it
                }
        }
    }

    fun resetItem() {
        _findItemByName.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
