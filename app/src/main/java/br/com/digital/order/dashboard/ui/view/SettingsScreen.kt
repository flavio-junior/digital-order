package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.StringsUtils.CHANGE_TO_OTHER_ACCOUNT

@Composable
fun SettingsScreen(
    goToLoginScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize36)
    ) {
        OptionButton(
            icon = R.drawable.sync,
            label = CHANGE_TO_OTHER_ACCOUNT,
            onClick = goToLoginScreen
        )
    }
}
