package br.com.digital.order.reservation.data.vo

import android.os.Parcelable
import br.com.digital.order.dashboard.domain.status.ReservationStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationResponseVO(
    val id: Long? = 0,
    val name: String? = "",
    val status: ReservationStatus? = null
): Parcelable
