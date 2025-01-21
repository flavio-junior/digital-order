package br.com.digital.order.reservation.data.repository

import android.content.Context
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.toResultFlow
import kotlinx.coroutines.flow.Flow

class ReservationRepository(
    private val context: Context,
    private val reservationRemoteImplDataSource: ReservationRemoteImplDataSource
) {

    fun finReservationByName(name: String): Flow<ObserveNetworkStateHandler<List<ReservationResponseDTO>>> {
        return toResultFlow(context = context) {
            reservationRemoteImplDataSource.finReservationByName(name = name)
        }
    }
}
