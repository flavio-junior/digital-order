package br.com.digital.order.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarColor(
    color: Color,
    darkIcons: Boolean = false
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = darkIcons
        }
    }
}
