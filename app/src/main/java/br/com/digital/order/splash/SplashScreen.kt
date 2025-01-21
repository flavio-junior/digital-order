package br.com.digital.order.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.account.data.vo.TokenResponseVO
import br.com.digital.order.account.ui.viewmodel.AccountViewModel
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.DELAY
import br.com.digital.order.utils.initializeWithDelay
import br.com.digital.order.utils.isNotBlankAndEmpty
import br.com.digital.order.utils.isTokenExpired
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    goToSignInScreen: () -> Unit = {},
    goToDashboardScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.secondary)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val viewModel: AccountViewModel = koinViewModel()
        LaunchedEffect(key1 = Unit) {
            initializeWithDelay(time = DELAY, action = { viewModel.getToken() })
        }
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(size = Themes.size.spaceSize500)
        )
        Title(
            title = stringResource(id = R.string.app_name),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        ObserveNetworkStateHandlerToken(
            viewModel = viewModel,
            goToSignInScreen = goToSignInScreen,
            goToDashboardScreen = goToDashboardScreen
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerToken(
    viewModel: AccountViewModel,
    goToSignInScreen: () -> Unit = {},
    goToDashboardScreen: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<TokenResponseVO> by remember { viewModel.getTokenSaved }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {},
        onSuccess = {
            if (state.result != null && state.result?.type?.isNotBlankAndEmpty() == true) {
                val checkContent = state.result
                if (checkContent?.type?.isNotBlankAndEmpty() == true) {
                    if (isTokenExpired(expirationDate = checkContent.expiration)) {
                        viewModel.cleanToken()
                        goToSignInScreen()
                    } else {
                        goToDashboardScreen()
                    }
                }
            } else {
                goToSignInScreen()
            }
        }
    )
}
