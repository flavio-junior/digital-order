package br.com.digital.order.order.data.repository

import android.content.Context
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import br.com.digital.order.order.data.dto.OrdersResponseDTO
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
}
