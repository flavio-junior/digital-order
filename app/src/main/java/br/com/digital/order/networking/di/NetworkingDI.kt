package br.com.digital.order.networking.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import br.com.digital.order.account.data.repository.remote.AccountRemoteDataSourceAPI
import br.com.digital.order.account.domain.converter.ConverterToken
import br.com.digital.order.retrofit.ProvideAccessTokenInterceptor
import br.com.digital.order.retrofit.ProvideAuthenticator
import br.com.digital.order.retrofit.ProvideRefreshTokenInterceptor
import br.com.digital.order.retrofit.provideAccessTokenOkHttpClient
import br.com.digital.order.retrofit.provideAuthenticatorOkHttpClient
import br.com.digital.order.retrofit.provideConverterFactory
import br.com.digital.order.retrofit.provideLoggingInterceptor
import br.com.digital.order.retrofit.provideRefreshTokenOkHttpClient
import br.com.digital.order.retrofit.provideRetrofit
import br.com.digital.order.utils.OrdersUtils.LOCAL_STORAGE
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import br.com.digital.order.account.data.repository.local.LocalStorage

val networkModule = module {
    single { provideConverterFactory() }
    single { provideLoggingInterceptor() }
    single { provideRetrofit(get(), get()) }

    single { ProvideAuthenticator(get(), get(), get()) }
    single { ProvideAccessTokenInterceptor(get()) }
    single { ProvideRefreshTokenInterceptor(get()) }
    single { provideAccessTokenOkHttpClient(get()) }
    single { provideAuthenticatorOkHttpClient(get(), get(), get()) }
    single { provideRefreshTokenOkHttpClient(get()) }

    single { ConverterToken() }
    single { get<Retrofit>().create(AccountRemoteDataSourceAPI::class.java) }

    singleOf(::LocalStorage)
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(name = LOCAL_STORAGE)
        }
    }
}

