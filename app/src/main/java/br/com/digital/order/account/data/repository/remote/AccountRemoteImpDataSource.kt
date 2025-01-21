package br.com.digital.order.account.data.repository.remote

import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.account.data.dto.SignInRequestDTO
import retrofit2.Response

class AccountRemoteImpDataSource(
    private val guidePointAPI: AccountAPI
) {

    suspend fun signIn(signInRequestDTO: SignInRequestDTO): Response<TokenResponseDTO> {
        return guidePointAPI.signIn(signInRequestDTO)
    }

    suspend fun refreshToken(email: String): Response<TokenResponseDTO> {
        return guidePointAPI.refreshToken(email = email)
    }
}
