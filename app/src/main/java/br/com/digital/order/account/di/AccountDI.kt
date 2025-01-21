package br.com.digital.order.account.di

import br.com.digital.order.account.data.repository.remote.AccountRemoteDataSourceAPI
import br.com.digital.order.account.data.repository.AccountRepository
import br.com.digital.order.account.data.repository.remote.AccountRemoteImpDataSource
import br.com.digital.order.account.ui.viewmodel.AccountViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val accountModule = module {
    factory { AccountRepository(androidContext(), get()) }
    factory { AccountRemoteImpDataSource(get()) }
    single { get<Retrofit>().create(AccountRemoteDataSourceAPI::class.java) }
    viewModel { AccountViewModel(get(), get(), get()) }
}
