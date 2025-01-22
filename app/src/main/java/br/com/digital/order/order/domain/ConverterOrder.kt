package br.com.digital.order.order.domain

import br.com.digital.order.common.domain.converterPageableDTOToVO
import br.com.digital.order.order.data.dto.AddressResponseDTO
import br.com.digital.order.order.data.dto.ObjectResponseDTO
import br.com.digital.order.order.data.dto.OrderResponseDTO
import br.com.digital.order.order.data.dto.OrdersResponseDTO
import br.com.digital.order.order.data.dto.OverviewResponseDTO
import br.com.digital.order.order.data.dto.PaymentResponseDTO
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.reservation.data.vo.AddressResponseVO
import br.com.digital.order.reservation.data.vo.ObjectResponseVO
import br.com.digital.order.reservation.data.vo.OverviewResponseVO
import br.com.digital.order.reservation.data.vo.PaymentResponseVO
import br.com.digital.order.reservation.data.vo.ReservationResponseVO

class ConverterOrder {

    fun converterContentDTOToVO(content: OrdersResponseDTO): OrdersResponseVO {
        return OrdersResponseVO(
            totalPages = content.totalPages,
            content = converterOrdersResponseDTOToVO(orders = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    fun converterOrderResponseDTOToVO(order: OrderResponseDTO): OrderResponseVO {
        return OrderResponseVO(
            id = order.id,
            createdAt = order.createdAt,
            qrCode = order.qrCode,
            type = order.type,
            status = order.status,
            reservations = converterReservationsResponseDTOToVO(reservations = order.reservations),
            address = converterAddressResponseDTOToVO(address = order.address),
            objects = converterObjectResponseDTOToVO(objects = order.objects),
            quantity = order.quantity,
            total = order.total,
            payment = converterPaymentResponseDTOToVO(payment = order.payment)
        )
    }

    private fun converterOrdersResponseDTOToVO(
        orders: List<OrderResponseDTO>
    ): List<OrderResponseVO> {
        return orders.map {
            OrderResponseVO(
                id = it.id,
                createdAt = it.createdAt,
                qrCode = it.qrCode,
                type = it.type,
                status = it.status,
                reservations = converterReservationsResponseDTOToVO(reservations = it.reservations),
                address = converterAddressResponseDTOToVO(address = it.address),
                objects = converterObjectResponseDTOToVO(objects = it.objects),
                quantity = it.quantity,
                total = it.total,
                payment = converterPaymentResponseDTOToVO(payment = it.payment)
            )
        }
    }

    private fun converterReservationsResponseDTOToVO(
        reservations: List<ReservationResponseDTO>? = null
    ): List<ReservationResponseVO>? {
        return reservations?.map {
            ReservationResponseVO(
                id = it.id,
                name = it.name
            )
        }
    }

    private fun converterAddressResponseDTOToVO(
        address: AddressResponseDTO? = null
    ): AddressResponseVO {
        return AddressResponseVO(
            id = address?.id,
            status = address?.status,
            complement = address?.complement,
            district = address?.district,
            number = address?.number,
            street = address?.street
        )
    }

    private fun converterObjectResponseDTOToVO(
        objects: List<ObjectResponseDTO>? = null
    ): List<ObjectResponseVO>? {
        return objects?.map {
            ObjectResponseVO(
                id = it.id,
                identifier = it.identifier,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                total = it.total,
                type = it.type,
                status = it.status,
                overview = converterOverviewResponseDTOToVO(objects = it.overview)
            )
        }
    }

    private fun converterOverviewResponseDTOToVO(
        objects: List<OverviewResponseDTO>? = null
    ): List<OverviewResponseVO> {
        return objects?.map {
            OverviewResponseVO(
                id = it.id,
                createdAt = it.createdAt,
                status = it.status,
                quantity = it.quantity
            )
        } ?: emptyList()
    }

    private fun converterPaymentResponseDTOToVO(
        payment: PaymentResponseDTO? = null
    ): PaymentResponseVO {
        return PaymentResponseVO(
            id = payment?.id ?: 0,
            date = payment?.date,
            hour = payment?.hour,
            code = payment?.code,
            typeOrder = payment?.typeOrder,
            typePayment = payment?.typePayment,
            discount = payment?.discount,
            valueDiscount = payment?.valueDiscount,
            total = payment?.total ?: 0.0
        )
    }
}
