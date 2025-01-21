package br.com.digital.order.reservation.data.repository

import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import retrofit2.Response

class ReservationRemoteImplDataSource(
    private val reservationRemoteDataSourceAPI: ReservationRemoteDataSourceAPI
) {

    suspend fun finReservationByName(name: String): Response<List<ReservationResponseDTO>> {
        return reservationRemoteDataSourceAPI.finReservationByName(name = name)
    }
}
