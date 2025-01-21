package br.com.digital.order.item.data.repository.remote

import br.com.digital.order.item.data.dto.ItemResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemRemoteDataSourceAPI {

    @GET("/api/dashboard/company/items/v1/find/item/by/{name}")
    suspend fun finFoodByName(@Path("name") name: String): Response<List<ItemResponseDTO>>
}
