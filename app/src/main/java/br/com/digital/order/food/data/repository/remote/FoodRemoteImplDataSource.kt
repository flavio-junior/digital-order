package br.com.digital.order.food.data.repository.remote

import br.com.digital.order.food.data.dto.FoodResponseDTO
import retrofit2.Response

class FoodRemoteImplDataSource(
    private val productRemoteDataSourceAPI: FoodRemoteDataSourceAPI
) {

    suspend fun finFoodByName(name: String): Response<List<FoodResponseDTO>> {
        return productRemoteDataSourceAPI.finFoodByName(name = name)
    }
}
