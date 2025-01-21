package br.com.digital.order.utils

import androidx.core.util.PatternsCompat

fun validateEmail(email: String): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
}

fun CharSequence.isBlankAndEmpty(): Boolean {
    return isBlank() && isEmpty()
}

fun CharSequence.isNotBlankAndEmpty(): Boolean {
    return isNotBlank() && isNotEmpty()
}
