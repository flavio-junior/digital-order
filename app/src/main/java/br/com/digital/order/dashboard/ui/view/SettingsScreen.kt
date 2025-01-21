package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.dashboard.ui.viewmodel.DashboardViewModel
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.StringsUtils.CHANGE_TO_OTHER_ACCOUNT
import br.com.digital.order.utils.StringsUtils.EXIT
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    goToLoginScreen: () -> Unit = {},
    goToSplashScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: DashboardViewModel = koinViewModel()
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize36),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        OptionButton(
            icon = R.drawable.sync,
            label = CHANGE_TO_OTHER_ACCOUNT,
            onClick = goToLoginScreen
        )
        OptionButton(
            icon = R.drawable.logout,
            label = EXIT,
            onClick = {
                viewModel.logoutApplication()
            }
        )
        ObserveStateLogoutApplication(
            viewModel = viewModel,
            goToSplashScreen = goToSplashScreen,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveStateLogoutApplication(
    viewModel: DashboardViewModel,
    goToSplashScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.logoutApplication }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            goToSplashScreen()
        }
    )
}
