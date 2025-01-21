package br.com.digital.order.account.data.repository.remote

import android.content.Context
import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import br.com.digital.order.account.data.dto.SignInRequestDTO
import kotlinx.coroutines.flow.Flow

class AccountRemoteDataSource(
    private val context: Context,
    private val accountRemoteImpDataSource: AccountRemoteImpDataSource
) {

    fun signIn(signInRequestDTO: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow(context = context) {
            accountRemoteImpDataSource.signIn(signInRequestDTO = signInRequestDTO)
        }
    }

    fun refreshToken(email: String): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow(context = context) {
            accountRemoteImpDataSource.refreshToken(email = email)
        }
    }
}
