package br.com.digital.order.product.data.repository

import br.com.digital.order.product.data.dto.ProductResponseDTO
import retrofit2.Response

class ProductRemoteImplDataSource(
    private val productRemoteDataSourceAPI: ProductRemoteDataSourceAPI
) {

    suspend fun finProductByName(name: String): Response<List<ProductResponseDTO>> {
        return productRemoteDataSourceAPI.finProductByName(name = name)
    }
}
