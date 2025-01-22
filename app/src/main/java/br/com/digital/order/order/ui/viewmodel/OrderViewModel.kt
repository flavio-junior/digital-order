package br.com.digital.order.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.repository.OrderRepository
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.order.domain.ConverterOrder
import br.com.digital.order.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.order.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.order.utils.StringsUtils.ASC
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository,
    private val converter: ConverterOrder
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllOpenOrders =
        mutableStateOf<ObserveNetworkStateHandler<OrdersResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllOpenOrders: State<ObserveNetworkStateHandler<OrdersResponseVO>> =
        _findAllOpenOrders

    fun findAllOpenOrders() {
        viewModelScope.launch {
            repository.findAllOpenOrders(
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllOpenOrders.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        if (objectConverted.content?.isNotEmpty() == true) {
                            _findAllOpenOrders.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        } else {
                            _findAllOpenOrders.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        }
                    }
                }
        }
    }
}
