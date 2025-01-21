package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.product.domain.factory.typeOrderFactory
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.TextField
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.OrdersUtils.NUMBER_EQUALS_ZERO
import br.com.digital.order.utils.StringsUtils
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import br.com.digital.order.utils.onBorder

@Composable
fun CardObjectSelect(
    objectRequestDTO: ObjectRequestDTO,
    quantity: Int = NUMBER_ZERO,
    verifyObject: Boolean = false,
    onItemSelected: (ObjectRequestDTO) -> Unit = {},
    onQuantityChange: (Int) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var newQuantity: Int by remember { mutableIntStateOf(value = quantity) }
    var observer: Pair<Boolean, String> by remember {
        mutableStateOf(value = Pair(first = false, second = EMPTY_TEXT))
    }
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
            .width(width = Themes.size.spaceSize200)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Title(
            title = typeOrderFactory(type = objectRequestDTO.type),
        )
        TextField(
            label = R.string.name,
            value = objectRequestDTO.name,
            enabled = false,
            isError = observer.first,
            keyboardType = KeyboardType.Number,
            onValueChange = {}
        )
        TextField(
            label = R.string.quantity,
            value = newQuantity.toString(),
            isError = observer.first,
            message = observer.second,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                newQuantity = it.toIntOrNull() ?: NUMBER_ZERO
            }
        )
        if (newQuantity > NUMBER_ZERO) {
            onQuantityChange(newQuantity)
        }
        if (verifyObject) {
            observer = if (quantity > NUMBER_ZERO) {
                Pair(first = false, second = EMPTY_TEXT)
            } else {
                Pair(first = true, second = NUMBER_EQUALS_ZERO)
            }
        }
        if (openDialog) {
            RemoveItem(
                onDismiss = {
                    openDialog = false
                },
                onItemSelected = {
                    openDialog = false
                    onItemSelected(objectRequestDTO)
                }
            )
        }
    }
}

@Composable
fun RemoveItem(
    onDismiss: () -> Unit = {},
    onItemSelected: () -> Unit = {}
) {
    SelectObject(
        onDismiss = onDismiss,
        body = {
            Title(
                title = StringsUtils.REMOVE_ITEM,
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
