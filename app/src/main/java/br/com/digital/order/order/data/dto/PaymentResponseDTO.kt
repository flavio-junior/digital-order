package br.com.digital.order.order.data.dto

import br.com.digital.order.dashboard.domain.type.PaymentType
import br.com.digital.order.dashboard.domain.type.TypeOrder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponseDTO(
    var id: Long = 0,
    var date: String? = "",
    var hour: String? = "",
    var code: Long? = 0,
    @SerialName("type_order")
    var typeOrder: TypeOrder? = null,
    @SerialName("type_payment")
    var typePayment: PaymentType? = null,
    var discount: Boolean? = null,
    @SerialName("value_discount")
    var valueDiscount: Double? = null,
    var total: Double = 0.0
)
