package br.com.digital.order.dashboard.data.repository.remote

import br.com.digital.order.dashboard.data.dto.OrderRequestDTO
import retrofit2.Response

class DashboardRemoteImpDataSource(
    private val dashboardRemoteDataSourceAPI: DashboardRemoteDataSourceAPI
) {

    suspend fun createNewOrder(order: OrderRequestDTO): Response<Unit> {
        return dashboardRemoteDataSourceAPI.createNewOrder(order = order)
    }
}
