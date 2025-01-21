package br.com.digital.order.item.data.repository.remote

import br.com.digital.order.item.data.dto.ItemResponseDTO
import retrofit2.Response

class ItemRemoteImplDataSource(
    private val productRemoteDataSourceAPI: ItemRemoteDataSourceAPI
) {

    suspend fun finItemByName(name: String): Response<List<ItemResponseDTO>> {
        return productRemoteDataSourceAPI.finFoodByName(name = name)
    }
}
