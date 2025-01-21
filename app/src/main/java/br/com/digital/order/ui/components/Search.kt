package br.com.digital.order.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.order.R
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.ui.theme.Typography
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.SEARCH
import br.com.digital.order.utils.colorTheme
import br.com.digital.order.utils.onClickable

@Composable
fun Search(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BodySearch(
            value = value,
            label = SEARCH,
            isError = isError,
            message = message,
            onValueChange = onValueChange,
            onGo = onGo
        )
    }
}

@Composable
private fun BodySearch(
    value: String = EMPTY_TEXT,
    label: String = EMPTY_TEXT,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var showIconSearchButton by remember { mutableStateOf(value = false) }
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showIconSearchButton = it.isNotBlank()
            },
            singleLine = true,
            label = {
                InfoText(text = label, color = Themes.colors.primary)
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (showIconSearchButton) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = SEARCH,
                        modifier = Modifier.onClickable(onClick = onGo),
                    )
                }
            },
            textStyle = Typography(color = Themes.colors.primary).infoText(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = { onGo() }
            ),
            colors = colorTheme(error = isError),
            shape = RoundedCornerShape(size = Themes.size.spaceSize16)
        )
        IsErrorMessage(isError = isError, message = message)
    }
}
