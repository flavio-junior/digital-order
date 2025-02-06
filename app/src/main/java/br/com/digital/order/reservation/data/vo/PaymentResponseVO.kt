package br.com.digital.order.reservation.data.vo

import android.os.Parcelable
import br.com.digital.order.dashboard.domain.type.PaymentType
import br.com.digital.order.dashboard.domain.type.TypeOrder
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentResponseVO(
    var id: Long = 0,
    var date: String? = "",
    var hour: String? = "",
    var code: Long? = 0,
    var typeOrder: TypeOrder? = null,
    var typePayment: PaymentType? = null,
    var discount: Boolean? = null,
    var valueDiscount: Double? = null,
    var total: Double = 0.0
): Parcelable
