package br.com.digital.order.account.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.account.data.dto.SignInRequestDTO
import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.account.ui.viewmodel.AccountViewModel
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.SimpleText
import br.com.digital.order.ui.components.TextField
import br.com.digital.order.ui.components.TextPassword
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ENTER_YOUR_ACCOUNT
import br.com.digital.order.utils.StringsUtils.FORGOT_PASS
import br.com.digital.order.utils.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.order.utils.isNotBlankAndEmpty
import br.com.digital.order.utils.onClickable
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SignInScreen(
    goToDashboardScreen: () -> Unit = {},
    goToSendRecoverTokenScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: AccountViewModel = koinViewModel()
    var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var password: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var isError: Boolean by remember { mutableStateOf(value = false) }
    var errorMessage: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var isEnabled: Boolean by remember { mutableStateOf(value = false) }
    val checkSignIn = { emailArg: String, passwordArg: String ->
        checkDataToSignIn(
            triple = Triple(first = emailArg, second = passwordArg, third = viewModel),
            onError = {
                isError = it.first
                isEnabled = it.second
                errorMessage = it.third
            }
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(all = Themes.size.spaceSize36)
            .wrapContentHeight(align = Alignment.CenterVertically),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        GetDataInputsSignIn(
            email = email,
            password = password,
            checkSignIn = checkSignIn,
            isError = isError,
            errorMessage = errorMessage,
            onValueChange = {
                email = it.first
                password = it.second
            }
        )
        ForgetPassword(goToSendRecoverTokenScreen = goToSendRecoverTokenScreen)
        LoadingButton(
            onClick = {
                checkSignIn(email, password)
            },
            isEnabled = isEnabled,
            label = ENTER_YOUR_ACCOUNT
        )
        ObserveStateSignIn(
            viewModel = viewModel,
            onError = {
                isError = it.first
                isEnabled = it.second
                errorMessage = it.third
            },
            goToDashboardScreen = goToDashboardScreen,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun GetDataInputsSignIn(
    email: String,
    password: String,
    checkSignIn: (String, String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (Pair<String, String>) -> Unit
) {
    var emailMutable by remember { mutableStateOf(value = email) }
    var passwordMutable by remember { mutableStateOf(value = password) }
    TextField(
        label = R.string.email,
        value = emailMutable,
        icon = R.drawable.mail,
        keyboardType = KeyboardType.Email,
        isError = isError,
        onValueChange = { emailMutable = it }
    )
    TextPassword(
        label = R.string.password,
        value = passwordMutable,
        isError = isError,
        message = errorMessage,
        onValueChange = { passwordMutable = it },
        onGo = { checkSignIn(emailMutable, passwordMutable) }
    )
    onValueChange(Pair(first = emailMutable, second = passwordMutable))
}

@Composable
private fun ForgetPassword(
    goToSendRecoverTokenScreen: () -> Unit = {}
) {
    SimpleText(
        text = FORGOT_PASS.lowercase(),
        modifier = Modifier
            .onClickable {
                goToSendRecoverTokenScreen()
            }
            .fillMaxWidth(),
        textAlign = TextAlign.End
    )
}

@Composable
private fun ObserveStateSignIn(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToDashboardScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<TokenResponseDTO> by remember {
        viewModel.signIn
    }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it.orEmpty()))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            state.result?.let {
                goToDashboardScreen()
            }
        }
    )
}

private fun checkDataToSignIn(
    triple: Triple<String, String, AccountViewModel>,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
) {
    if (triple.first.isNotBlankAndEmpty() && triple.second.isNotBlankAndEmpty()) {
        onError(Triple(first = false, second = true, third = EMPTY_TEXT))
        triple.third.signIn(SignInRequestDTO(email = triple.first, password = triple.second))
    } else {
        onError(Triple(first = true, second = false, third = NOT_BLANK_OR_EMPTY))
    }
}
