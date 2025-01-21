package br.com.digital.order.dashboard.data.dto

import br.com.digital.order.dashboard.domain.type.PaymentType
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequestDTO(
    var type: PaymentType? = null,
    var discount: Boolean? = null,
    var value: Double? = 0.0
)
