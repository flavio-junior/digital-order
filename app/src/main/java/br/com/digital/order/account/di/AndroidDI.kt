package br.com.digital.order.account.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import br.com.digital.order.account.data.repository.AccountRepository
import br.com.digital.order.account.data.repository.local.LocalStorage
import br.com.digital.order.account.data.repository.remote.AccountRemoteDataSource
import br.com.digital.order.utils.OrdersUtils.LOCAL_STORAGE
import br.com.digital.order.account.domain.converter.ConverterToken
import br.com.digital.order.account.ui.viewmodel.AccountViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    singleOf(::LocalStorage)
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(name = LOCAL_STORAGE)
        }
    }
    single<AccountRepository> { AccountRemoteDataSource(get()) }
    single { ConverterToken() }
    single { AccountViewModel(get(), get(), get()) }
}
