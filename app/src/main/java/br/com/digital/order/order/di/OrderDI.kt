package br.com.digital.order.order.di

import br.com.digital.order.order.data.repository.OrderRemoteDataSourceAPI
import br.com.digital.order.order.data.repository.OrderRemoteImplDataSource
import br.com.digital.order.order.data.repository.OrderRepository
import br.com.digital.order.order.domain.ConverterOrder
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val orderModule = module {
    single { ConverterOrder() }
    factory { OrderRepository(androidContext(), get()) }
    factory { OrderRemoteImplDataSource(get()) }
    single { get<Retrofit>().create(OrderRemoteDataSourceAPI::class.java) }
    viewModel { OrderViewModel(get(), get()) }
}