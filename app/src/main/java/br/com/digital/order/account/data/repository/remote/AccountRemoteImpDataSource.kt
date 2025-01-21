package br.com.digital.order.account.data.repository.remote

import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.account.data.dto.SignInRequestDTO
import retrofit2.Response

class AccountRemoteImpDataSource(
    private val accountRemoteDataSourceAPI: AccountRemoteDataSourceAPI
) {

    suspend fun signIn(signInRequestDTO: SignInRequestDTO): Response<TokenResponseDTO> {
        return accountRemoteDataSourceAPI.signIn(signInRequestDTO)
    }

    suspend fun refreshToken(email: String): Response<TokenResponseDTO> {
        return accountRemoteDataSourceAPI.refreshToken(email = email)
    }
}
