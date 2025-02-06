package br.com.digital.order.reservation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.SimpleText
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import br.com.digital.order.utils.onBorder

@Composable
fun ReservationSelected(
    reservation: ReservationResponseDTO,
    onItemSelected: (ReservationResponseDTO) -> Unit = {}
) {
    var openDialog by remember { mutableStateOf(value = false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    openDialog = true
                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .height(height = Themes.size.spaceSize40)
            .width(width = Themes.size.spaceSize100),
        verticalArrangement = Arrangement.Center
    ) {
        SimpleText(
            text = reservation.name ?: EMPTY_TEXT,
            color = Themes.colors.primary
        )
        if (openDialog) {
            RemoveReservation(
                onDismiss = {
                    openDialog = false
                },
                onItemSelected = {
                    onItemSelected(reservation)
                    openDialog = false
                }
            )
        }
    }
}

@Composable
private fun RemoveReservation(
    onDismiss: () -> Unit = {},
    onItemSelected: () -> Unit = {}
) {
    SelectObject(
        onDismiss = onDismiss,
        body = {
            Title(
                title = StringsUtils.REMOVER_RESERVATION,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = onItemSelected
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = onDismiss
            )
        }
    )
}
