package br.com.digital.order.order.data.repository

import br.com.digital.order.order.data.dto.OrdersResponseDTO
import br.com.digital.order.utils.NumbersUtils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderRemoteDataSourceAPI {

    @GET("/api/dashboard/company/orders/v1/open")
    suspend fun findAllOpenOrders(
        @Query("page") page: Int,
        @Query("size") size: Int = NumbersUtils.NUMBER_SIXTY,
        @Query("sort") sort: String
    ): Response<OrdersResponseDTO>
}
