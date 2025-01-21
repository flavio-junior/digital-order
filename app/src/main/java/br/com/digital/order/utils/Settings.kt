package br.com.digital.order.utils

import kotlinx.coroutines.delay

suspend fun initializeWithDelay(
    time: Long,
    action: () -> Unit = {}
) {
    delay(timeMillis = time)
    action()
}
