package br.com.digital.order.product.di

import br.com.digital.order.product.data.repository.ProductRemoteDataSourceAPI
import br.com.digital.order.product.data.repository.ProductRemoteImplDataSource
import br.com.digital.order.product.data.repository.ProductRepository
import br.com.digital.order.product.domain.converter.ConverterProduct
import br.com.digital.order.product.ui.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val productModule = module {
    single { ConverterProduct(get()) }
    factory { ProductRepository(androidContext(), get()) }
    factory { ProductRemoteImplDataSource(get()) }
    single { get<Retrofit>().create(ProductRemoteDataSourceAPI::class.java) }
    viewModel { ProductViewModel(get()) }
}
