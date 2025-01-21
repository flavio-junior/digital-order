package br.com.digital.order.item.data.repository

import android.content.Context
import br.com.digital.order.item.data.dto.ItemResponseDTO
import br.com.digital.order.item.data.repository.remote.ItemRemoteImplDataSource
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import kotlinx.coroutines.flow.Flow

class ItemRepository(
    private val context: Context,
    private val productRemoteImpDataSource: ItemRemoteImplDataSource
) {

    fun finItemByName(name: String): Flow<ObserveNetworkStateHandler<List<ItemResponseDTO>>> {
        return toResultFlow(context = context) {
            productRemoteImpDataSource.finItemByName(name = name)
        }
    }
}
