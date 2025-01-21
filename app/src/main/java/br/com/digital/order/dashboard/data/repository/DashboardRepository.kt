package br.com.digital.order.dashboard.data.repository

import android.content.Context
import br.com.digital.order.dashboard.data.dto.OrderRequestDTO
import br.com.digital.order.dashboard.data.repository.remote.DashboardRemoteImpDataSource
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import kotlinx.coroutines.flow.Flow

class DashboardRepository(
    private val context: Context,
    private val dashboardRemoteImpDataSource: DashboardRemoteImpDataSource
) {

    fun createNewOrder(
        order: OrderRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow(context = context) {
            dashboardRemoteImpDataSource.createNewOrder(order = order)
        }
    }
}