package br.com.digital.order.food.data.repository

import android.content.Context
import br.com.digital.order.food.data.dto.FoodResponseDTO
import br.com.digital.order.food.data.repository.remote.FoodRemoteImplDataSource
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val context: Context,
    private val productRemoteImpDataSource: FoodRemoteImplDataSource
) {

    fun finFoodByName(name: String): Flow<ObserveNetworkStateHandler<List<FoodResponseDTO>>> {
        return toResultFlow(context = context) {
            productRemoteImpDataSource.finFoodByName(name = name)
        }
    }
}
