package br.com.digital.order.order.utils

import br.com.digital.order.dashboard.domain.status.ObjectStatus
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.reservation.data.vo.ReservationResponseVO

object OrderUtils {
    const val NUMBER_ITEMS = "Número de itens:"
    const val NUMBER_RESERVATIONS = "Número de reservas:"
}


fun converterReservationVOtoDTO(
    reservation: ReservationResponseVO
): ReservationResponseDTO {
    return ReservationResponseDTO(
        id = reservation.id,
        name = reservation.name
    )
}

fun countPendingObjects(order: OrderResponseVO): String {
    val numberObjects = order.objects?.count { it.status == ObjectStatus.PENDING } ?: 0
    return numberObjects.toString()
}
