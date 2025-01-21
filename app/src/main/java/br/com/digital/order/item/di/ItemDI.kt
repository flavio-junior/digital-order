package br.com.digital.order.item.di

import br.com.digital.order.item.data.repository.ItemRepository
import br.com.digital.order.item.data.repository.remote.ItemRemoteDataSourceAPI
import br.com.digital.order.item.data.repository.remote.ItemRemoteImplDataSource
import br.com.digital.order.item.ui.viewmodel.ItemViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val itemModule = module {
    factory { ItemRepository(androidContext(), get()) }
    factory { ItemRemoteImplDataSource(get()) }
    single { get<Retrofit>().create(ItemRemoteDataSourceAPI::class.java) }
    viewModel { ItemViewModel(get()) }
}
