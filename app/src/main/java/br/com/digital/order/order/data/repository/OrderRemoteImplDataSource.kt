package br.com.digital.order.order.data.repository

import br.com.digital.order.order.data.dto.OrdersResponseDTO
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
}
