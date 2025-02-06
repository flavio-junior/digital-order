package br.com.digital.order.order.data.repository

import android.content.Context
import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import br.com.digital.order.order.data.dto.OrdersResponseDTO
import br.com.digital.order.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import kotlinx.coroutines.flow.Flow

class OrderRepository(
    private val context: Context,
    private val orderRemoteImplDataSource: OrderRemoteImplDataSource
) {

    fun findAllOpenOrders(
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<OrdersResponseDTO>> {
        return toResultFlow(context = context) {
            orderRemoteImplDataSource.findAllOpenOrders(
                page = page,
                size = size,
                sort = sort
            )
        }
    }

    fun updateOrder(
        orderId: Long,
        objectId: Long,
        updateObject: UpdateObjectRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow(context = context) {
            orderRemoteImplDataSource.updateOrder(
                orderId = orderId,
                objectId = objectId,
                updateObject = updateObject
            )
        }
    }

    fun incrementMoreObjectsOrder(
        orderId: Long,
        incrementObjects: List<ObjectRequestDTO>
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow(context = context) {
            orderRemoteImplDataSource.incrementMoreObjectsOrder(
                orderId = orderId,
                incrementObjects = incrementObjects
            )
        }
    }

    fun incrementMoreReservationsOrder(
        orderId: Long,
        reservationsToSava: List<ReservationResponseDTO>
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow(context = context) {
            orderRemoteImplDataSource.incrementMoreReservationsOrder(
                orderId = orderId,
                reservationsToSava = reservationsToSava
            )
        }
    }

    fun removeReservationOrder(
        orderId: Long,
        reservationId: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow(context = context) {
            orderRemoteImplDataSource.removeReservationOrder(
                orderId = orderId,
                reservationId = reservationId
            )
        }
    }

    fun deleteOrder(
        orderId: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow(context = context) {
            orderRemoteImplDataSource.deleteOrder(orderId = orderId)
        }
    }
}
