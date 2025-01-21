package br.com.digital.order.retrofit

import br.com.digital.order.account.data.repository.local.LocalStorage
import br.com.digital.order.account.data.repository.remote.AccountAPI
import br.com.digital.order.account.domain.converter.ConverterToken
import br.com.digital.order.utils.OrdersUtils.TIME_SECONDS
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

fun provideAuthenticatorOkHttpClient(
    localStorage: LocalStorage,
    converterToken: ConverterToken,
    networkingAPI: AccountAPI
): OkHttpClient {
    return OkHttpClient
        .Builder()
        .authenticator(
            ProvideAuthenticator(
                localStorage = localStorage,
                converterToken = converterToken,
                networkingAPI = networkingAPI
            )
        )
        .addInterceptor(interceptor = provideLoggingInterceptor())
        .readTimeout(timeout = TIME_SECONDS, unit = TimeUnit.SECONDS)
        .connectTimeout(timeout = TIME_SECONDS, unit = TimeUnit.SECONDS)
        .build()
}

fun provideAccessTokenOkHttpClient(
    localStorage: LocalStorage
): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(interceptor = provideLoggingInterceptor())
        .addInterceptor(interceptor = ProvideAccessTokenInterceptor(localStorage = localStorage))
        .readTimeout(timeout = TIME_SECONDS, unit = TimeUnit.SECONDS)
        .connectTimeout(timeout = TIME_SECONDS, unit = TimeUnit.SECONDS)
        .build()
}

fun provideRefreshTokenOkHttpClient(
    localStorage: LocalStorage
): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor(interceptor = provideLoggingInterceptor())
        .addInterceptor(interceptor = ProvideRefreshTokenInterceptor(localStorage = localStorage))
        .readTimeout(timeout = TIME_SECONDS, unit = TimeUnit.SECONDS)
        .connectTimeout(timeout = TIME_SECONDS, unit = TimeUnit.SECONDS)
        .build()
}
