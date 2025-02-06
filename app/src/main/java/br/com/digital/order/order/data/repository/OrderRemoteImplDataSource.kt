package br.com.digital.order.order.data.repository

import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.dto.OrdersResponseDTO
import br.com.digital.order.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class OrderRemoteImplDataSource(
    private val orderRemoteDataSourceAPI: OrderRemoteDataSourceAPI
) {

    suspend fun findAllOpenOrders(
        page: Int,
        size: Int,
        sort: String
    ): Response<OrdersResponseDTO> {
        return orderRemoteDataSourceAPI.findAllOpenOrders(page = page, size = size, sort = sort)
    }

    suspend fun updateOrder(
        orderId: Long,
        objectId: Long,
        updateObject: UpdateObjectRequestDTO
    ): Response<Unit> {
        return orderRemoteDataSourceAPI.updateOrder(
            orderId = orderId,
            objectId = objectId,
            updateObject = updateObject
        )
    }

    suspend fun incrementMoreObjectsOrder(
        orderId: Long,
        incrementObjects: List<ObjectRequestDTO>
    ): Response<Unit> {
        return orderRemoteDataSourceAPI.incrementMoreObjectsOrder(
            orderId = orderId,
            incrementObjects = incrementObjects
        )
    }

    suspend fun incrementMoreReservationsOrder(
        orderId: Long,
        reservationsToSava: List<ReservationResponseDTO>
    ): Response<Unit> {
        return orderRemoteDataSourceAPI.incrementMoreReservationsOrder(
            orderId = orderId,
            reservationsToSava = reservationsToSava
        )
    }

    suspend fun removeReservationOrder(
        orderId: Long,
        reservationId: Long
    ): Response<Unit> {
        return orderRemoteDataSourceAPI.removeReservationOrder(
            orderId = orderId,
            reservationId = reservationId
        )
    }

    suspend fun deleteOrder(
        orderId: Long
    ): Response<Unit> {
        return orderRemoteDataSourceAPI.deleteOrder(orderId = orderId)
    }
}
