package br.com.digital.order.product.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.product.data.dto.ProductResponseDTO
import br.com.digital.order.product.data.repository.ProductRepository
import br.com.digital.order.product.domain.ConverterProduct
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository,
    private val converter: ConverterProduct
): ViewModel() {

    private val _findProductByName =
        mutableStateOf<ObserveNetworkStateHandler<List<ProductResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findProductByName: State<ObserveNetworkStateHandler<List<ProductResponseDTO>>> =
        _findProductByName

    fun findProductByName(name: String) {
        viewModelScope.launch {
            repository.finProductByName(name = name)
                .onStart {
                    _findProductByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findProductByName.value = it
                }
        }
    }

    fun resetProduct() {
        _findProductByName.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}