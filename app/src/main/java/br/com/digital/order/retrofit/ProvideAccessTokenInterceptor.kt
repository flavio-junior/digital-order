package br.com.digital.order.retrofit

import br.com.digital.order.account.data.repository.local.LocalStorage
import br.com.digital.order.utils.OrdersUtils.AUTHORIZATION
import br.com.digital.order.utils.OrdersUtils.BEARER
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ProvideAccessTokenInterceptor(
    private val localStorage: LocalStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        runBlocking {
            localStorage.getToken().let {
                requestBuilder.addHeader(name = AUTHORIZATION, value = "$BEARER ${it.accessToken}")
            }
        }
        return chain.proceed(request = requestBuilder.build())
    }
}
