package br.com.digital.order.product.data.repository

import android.content.Context
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import br.com.digital.order.product.data.dto.ProductResponseDTO
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val context: Context,
    private val productRemoteImpDataSource: ProductRemoteImplDataSource
) {

    fun finProductByName(name: String): Flow<ObserveNetworkStateHandler<List<ProductResponseDTO>>> {
        return toResultFlow(context = context) {
            productRemoteImpDataSource.finProductByName(name = name)
        }
    }
}
