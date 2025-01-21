package br.com.digital.order.retrofit

import br.com.digital.order.account.data.repository.local.LocalStorage
import br.com.digital.order.account.data.repository.remote.AccountRemoteDataSourceAPI
import br.com.digital.order.account.data.vo.TokenResponseVO
import br.com.digital.order.account.domain.converter.ConverterToken
import br.com.digital.order.utils.OrdersUtils.AUTHORIZATION
import br.com.digital.order.utils.OrdersUtils.BEARER
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class ProvideAuthenticator(
    private val localStorage: LocalStorage,
    private val converterToken: ConverterToken,
    private val networkingAPI: AccountRemoteDataSourceAPI
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken: TokenResponseVO = runBlocking {
            localStorage.getToken()
        }
        synchronized(lock = this) {
            val updatedToken: Any = runBlocking {
                localStorage.getToken()
            }
            val token = if (currentToken != updatedToken) {
                updatedToken
            } else {
                val updateToken = runBlocking {
                    networkingAPI.refreshToken(email = currentToken.user)
                }
                if (updateToken.isSuccessful && updateToken.body() != null) {
                    updateToken.body()?.let { body ->
                        runBlocking {
                            localStorage.saveToken(
                                converterToken.converterTokenRequestDTOToTokenResponseVO(body)
                            )
                        }
                    }
                } else {
                    null
                }
            }
            return if (token != null) {
                response.request.newBuilder()
                    .header(name = AUTHORIZATION, value = "$BEARER $token")
                    .build()
            } else {
                null
            }
        }
    }
}
