package br.com.digital.order.dashboard.ui.view

import androidx.annotation.StringRes
import br.com.digital.order.R

enum class BottomNavigationItem {
    PDV,
    ORDERS,
    SETTINGS
}

sealed class BottomNavigationRoute(
    @StringRes val label: Int,
    val icon: Int,
    val route: BottomNavigationItem
) {
    data object PDV : BottomNavigationRoute(
        R.string.pdv,
        R.drawable.shopping_cart,
        BottomNavigationItem.PDV
    )

    data object PendingOrders : BottomNavigationRoute(
        R.string.orders,
        R.drawable.order,
        BottomNavigationItem.ORDERS
    )

    data object Settings : BottomNavigationRoute(
        R.string.SETTINGS,
        R.drawable.settings,
        BottomNavigationItem.SETTINGS
    )
}
