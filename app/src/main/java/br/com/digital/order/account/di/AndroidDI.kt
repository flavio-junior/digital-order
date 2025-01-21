package br.com.digital.order.account.di

import br.com.digital.order.account.data.repository.remote.AccountAPI
import br.com.digital.order.account.data.repository.remote.AccountRemoteDataSource
import br.com.digital.order.account.data.repository.remote.AccountRemoteImpDataSource
import br.com.digital.order.account.ui.viewmodel.AccountViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val androidModule = module {
    factory { AccountRemoteDataSource(androidContext(), get()) }
    factory { AccountRemoteImpDataSource(get()) }
    single { get<Retrofit>().create(AccountAPI::class.java) }
    viewModel { AccountViewModel(get(), get(), get()) }
}
