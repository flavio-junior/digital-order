package br.com.digital.order.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.dashboard.domain.others.Action
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.order.order.data.repository.OrderRepository
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.order.domain.converter.ConverterOrder
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
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

    private val _updateStatusOverview =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateStatusOverview: State<ObserveNetworkStateHandler<Unit>> = _updateStatusOverview

    private val _incrementOverview =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementOverview: State<ObserveNetworkStateHandler<Unit>> = _incrementOverview

    private val _removeOverview =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeOverview: State<ObserveNetworkStateHandler<Unit>> = _removeOverview

    private val _removeObject =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeObject: State<ObserveNetworkStateHandler<Unit>> = _removeObject

    private val _incrementMoreObjectsOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementMoreObjectsOrder: State<ObserveNetworkStateHandler<Unit>> =
        _incrementMoreObjectsOrder

    private val _incrementMoreReservationsOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val incrementMoreReservationsOrder: State<ObserveNetworkStateHandler<Unit>> =
        _incrementMoreReservationsOrder

    private val _removeReservationOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val removeReservationOrder: State<ObserveNetworkStateHandler<Unit>> =
        _removeReservationOrder

    private val _deleteOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteOrder: State<ObserveNetworkStateHandler<Unit>> = _deleteOrder

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

    fun updateOrder(orderId: Long, objectId: Long, updateObject: UpdateObjectRequestDTO) {
        viewModelScope.launch {
            when (updateObject.action) {
                Action.UPDATE_STATUS_OVERVIEW -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _updateStatusOverview.value =
                                ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _updateStatusOverview.value = it
                        }
                }

                Action.INCREMENT_OVERVIEW -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _incrementOverview.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _incrementOverview.value = it
                        }
                }

                Action.REMOVE_OVERVIEW -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _removeOverview.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _removeOverview.value = it
                        }
                }

                Action.REMOVE_OBJECT -> {
                    repository.updateOrder(
                        orderId = orderId,
                        objectId = objectId,
                        updateObject = updateObject
                    )
                        .onStart {
                            _removeObject.value = ObserveNetworkStateHandler.Loading(l = true)
                        }
                        .collect {
                            _removeObject.value = it
                        }
                }
            }
        }
    }

    fun incrementMoreObjectsOrder(
        orderId: Long,
        incrementObjects: List<ObjectRequestDTO>
    ) {
        viewModelScope.launch {
            repository.incrementMoreObjectsOrder(
                orderId = orderId,
                incrementObjects = incrementObjects
            )
                .onStart {
                    _incrementMoreObjectsOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _incrementMoreObjectsOrder.value = it
                }
        }
    }

    fun incrementMoreReservationsOrder(
        orderId: Long,
        reservationsToSava: List<ReservationResponseDTO>
    ) {
        viewModelScope.launch {
            repository.incrementMoreReservationsOrder(
                orderId = orderId,
                reservationsToSava = reservationsToSava
            )
                .onStart {
                    _incrementMoreReservationsOrder.value =
                        ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _incrementMoreReservationsOrder.value = it
                }
        }
    }

    fun removeReservationOrder(
        orderId: Long,
        reservationId: Long
    ) {
        viewModelScope.launch {
            repository.removeReservationOrder(
                orderId = orderId,
                reservationId = reservationId
            )
                .onStart {
                    _removeReservationOrder.value =
                        ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _removeReservationOrder.value = it
                }
        }
    }

    fun deleteOrder(orderId: Long) {
        viewModelScope.launch {
            repository.deleteOrder(orderId = orderId)
                .onStart {
                    _deleteOrder.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteOrder.value = it
                }
        }
    }

    fun resetOrder(reset: ResetOrder) {
        when (reset) {
            ResetOrder.INCREMENT_OVERVIEW -> {
                _incrementOverview.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.REMOVE_OVERVIEW -> {
                _removeOverview.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.UPDATE_STATUS_OVERVIEW -> {
                _updateStatusOverview.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.REMOVE_OBJECT -> {
                _removeObject.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.INCREMENT_MORE_OBJECTS_ORDER -> {
                _incrementMoreObjectsOrder.value =
                    ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.INCREMENT_MORE_RESERVATIONS_ORDER -> {
                _incrementMoreReservationsOrder.value =
                    ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.REMOVE_RESERVATION -> {
                _removeReservationOrder.value =
                    ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetOrder.DELETE_ORDER -> {
                _deleteOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetOrder {
    INCREMENT_OVERVIEW,
    REMOVE_OVERVIEW,
    UPDATE_STATUS_OVERVIEW,
    REMOVE_OBJECT,
    INCREMENT_MORE_RESERVATIONS_ORDER,
    REMOVE_RESERVATION,
    INCREMENT_MORE_OBJECTS_ORDER,
    DELETE_ORDER
}
