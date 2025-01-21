package br.com.digital.order.product.data.repository

import br.com.digital.order.product.data.dto.ProductResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRemoteDataSourceAPI {

    @GET("/api/dashboard/company/products/v1/find/product/by/{name}")
    suspend fun finProductByName(@Path("name") name: String): Response<List<ProductResponseDTO>>
}
