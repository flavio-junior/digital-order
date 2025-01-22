package br.com.digital.order.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.StringsUtils.LOADING

@Composable
fun LoadingData() {
    Box(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Themes.colors.primary,
                modifier = Modifier.size(size = Themes.size.spaceSize64),
                strokeWidth = Themes.size.spaceSize4
            )
            Spacer(modifier = Modifier.size(size = Themes.size.spaceSize32))
            Title(title = LOADING)
        }
    }
}
