package br.com.digital.order.dashboard.data.repository.remote

import br.com.digital.order.dashboard.data.dto.OrderRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DashboardRemoteDataSourceAPI {

    @POST("/api/digital/order/orders/v1")
    suspend fun createNewOrder(@Body order: OrderRequestDTO): Response<Unit>
}
