package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.theme.Themes

@Composable
fun ReservationScreen(
    goToBack: () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.reservations,
            goToBack = goToBack
        )
    }
}
