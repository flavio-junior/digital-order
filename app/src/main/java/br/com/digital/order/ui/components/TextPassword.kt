package br.com.digital.order.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import br.com.digital.order.R
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.ui.theme.Typography
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.colorTheme

@Composable
fun TextPassword(
    label: Int,
    value: String,
    imeAction: ImeAction = ImeAction.Go,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit = {}
) {
    var passwordHidden by rememberSaveable { mutableStateOf(value = true) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = Typography(color = Themes.colors.primary).simpleText(),
        singleLine = true,
        label = {
            Description(description = stringResource(id = label))
        },
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                contentDescription = stringResource(id = label),
                tint = Themes.colors.primary
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onGo = {
            onGo()
        }),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(
                    painter = if (passwordHidden) {
                        painterResource(id = R.drawable.visibility)
                    } else {
                        painterResource(id = R.drawable.visibility_off)
                    },
                    contentDescription = description,
                    tint = Themes.colors.primary
                )
            }
        },
        colors = colorTheme(error = isError),
        shape = RoundedCornerShape(size = Themes.size.spaceSize16)
    )
    IsErrorMessage(isError = isError, message = message)
}
