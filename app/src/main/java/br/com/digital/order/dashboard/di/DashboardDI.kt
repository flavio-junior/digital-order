package br.com.digital.order.dashboard.di

import br.com.digital.order.dashboard.data.repository.DashboardRepository
import br.com.digital.order.dashboard.data.repository.remote.DashboardRemoteDataSourceAPI
import br.com.digital.order.dashboard.data.repository.remote.DashboardRemoteImpDataSource
import br.com.digital.order.dashboard.ui.viewmodel.DashboardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val dashboardModule = module {
    factory { DashboardRepository(androidContext(), get()) }
    factory { DashboardRemoteImpDataSource(get()) }
    single { get<Retrofit>().create(DashboardRemoteDataSourceAPI::class.java) }
    viewModel { DashboardViewModel(get(), get()) }
}