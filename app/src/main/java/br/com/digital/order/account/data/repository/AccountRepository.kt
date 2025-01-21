package br.com.digital.order.account.data.repository

import android.content.Context
import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import br.com.digital.order.account.data.dto.SignInRequestDTO
import br.com.digital.order.account.data.repository.remote.AccountRemoteImpDataSource
import kotlinx.coroutines.flow.Flow

class AccountRepository(
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
