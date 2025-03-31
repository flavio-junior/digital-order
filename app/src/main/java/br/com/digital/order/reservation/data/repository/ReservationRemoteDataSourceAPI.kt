package br.com.digital.order.reservation.data.repository

import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservationRemoteDataSourceAPI {

    @GET("/api/digital/order/reservations/v1/find/reservation/by/{name}")
    suspend fun finReservationByName(@Path("name") name: String): Response<List<ReservationResponseDTO>>
}
