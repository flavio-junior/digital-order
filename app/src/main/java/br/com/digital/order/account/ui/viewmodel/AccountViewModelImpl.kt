package br.com.digital.order.account.ui.viewmodel

import br.com.digital.order.account.data.dto.SignInRequestDTO
import br.com.digital.order.account.data.dto.TokenResponseDTO

internal interface AccountViewModelImpl {
    fun signIn(signInRequestDTO: SignInRequestDTO)
    fun getToken()
    fun saveToken(token: TokenResponseDTO)
    fun refreshToken(email: String? = null, refresh: RefreshToken)
}

enum class RefreshToken {
    REFRESH_TOKEN,
    CLEAN_TOKEN
}
