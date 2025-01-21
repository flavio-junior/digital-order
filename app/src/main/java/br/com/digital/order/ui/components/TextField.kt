package br.com.digital.order.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.order.R
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.ui.theme.Typography
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.colorTheme
import br.com.digital.order.utils.onClickable


@Composable
fun TextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: Int,
    value: String,
    icon: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(value = false) }
    if (enabled) {
        if (value.isNotBlank()) showClearButton = true
    }
    OutlinedTextField(
        enabled = enabled,
        value = value,
        onValueChange = {
            onValueChange(it)
            showClearButton = it.isNotBlank()
        },
        singleLine = true,
        label = {
            Description(description = stringResource(id = label), color = Themes.colors.primary)
        },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = stringResource(id = label),
                    tint = Themes.colors.primary
                )
            }
        },
        trailingIcon = {
            if (showClearButton) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = Themes.colors.primary,
                    modifier = modifier.onClickable {
                        onValueChange(EMPTY_TEXT)
                    }
                )
            }
        },
        textStyle = Typography(color = Themes.colors.primary).simpleText(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onGo = {
            onGo()
        }),
        colors = colorTheme(error = isError),
        shape = RoundedCornerShape(size = Themes.size.spaceSize16)
    )
    IsErrorMessage(isError = isError, message = message)
}
