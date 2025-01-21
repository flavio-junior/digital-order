package br.com.digital.order.food.di

import br.com.digital.order.food.data.repository.FoodRepository
import br.com.digital.order.food.data.repository.remote.FoodRemoteDataSourceAPI
import br.com.digital.order.food.data.repository.remote.FoodRemoteImplDataSource
import br.com.digital.order.food.ui.viewmodel.FoodViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val foodModule = module {
    factory { FoodRepository(androidContext(), get()) }
    factory { FoodRemoteImplDataSource(get()) }
    single { get<Retrofit>().create(FoodRemoteDataSourceAPI::class.java) }
    viewModel { FoodViewModel(get()) }
}
