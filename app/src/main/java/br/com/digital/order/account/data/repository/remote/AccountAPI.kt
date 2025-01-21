package br.com.digital.order.account.data.repository.remote

import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.account.data.dto.SignInRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountAPI {

    @POST("/api/auth/v1/signIn")
    suspend fun signIn(@Body signInRequestDTO: SignInRequestDTO): Response<TokenResponseDTO>

    @Headers("Content-Type: application/json")
    @PUT("/api/auth/v1/refresh/{email}")
    suspend fun refreshToken(@Path("email") email: String): Response<TokenResponseDTO>
}
