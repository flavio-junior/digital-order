package br.com.digital.order.networking.resources

data class DescriptionError(
    val code: Int? = null,
    val type: ErrorType,
    val message: String? = null
)
