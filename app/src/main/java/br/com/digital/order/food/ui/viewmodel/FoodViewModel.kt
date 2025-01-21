package br.com.digital.order.food.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.food.data.repository.FoodRepository
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.food.data.dto.FoodResponseDTO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FoodViewModel(
    private val repository: FoodRepository
): ViewModel() {

    private val _findFoodByName =
        mutableStateOf<ObserveNetworkStateHandler<List<FoodResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findFoodByName: State<ObserveNetworkStateHandler<List<FoodResponseDTO>>> =
        _findFoodByName

    fun findFoodByName(name: String) {
        viewModelScope.launch {
            repository.finFoodByName(name = name)
                .onStart {
                    _findFoodByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findFoodByName.value = it
                }
        }
    }

    fun resetFood() {
        _findFoodByName.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
