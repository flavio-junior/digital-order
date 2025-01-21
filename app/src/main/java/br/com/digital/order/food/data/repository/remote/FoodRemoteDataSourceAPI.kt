package br.com.digital.order.food.data.repository.remote

import br.com.digital.order.food.data.dto.FoodResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodRemoteDataSourceAPI {

    @GET("/api/dashboard/company/foods/v1/find/food/by/{name}")
    suspend fun finFoodByName(@Path("name") name: String): Response<List<FoodResponseDTO>>
}
