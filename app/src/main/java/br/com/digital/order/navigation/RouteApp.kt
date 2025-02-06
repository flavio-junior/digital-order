package br.com.digital.order.navigation

sealed class RouteApp(val item: String) {
    data object SplashScreen : RouteApp(item = ItemNavigation.SPLASH_SCREEN.name)
    data object SignIn : RouteApp(item = ItemNavigation.SIGN_IN.name)
    data object Dashboard : RouteApp(item = ItemNavigation.DASHBOARD.name)
    data object Delivery : RouteApp(item = ItemNavigation.DELIVERY.name)
    data object Order : RouteApp(item = ItemNavigation.ORDER.name)
    data object Reservation : RouteApp(item = ItemNavigation.RESERVATION.name)
    data object OrderDetail : RouteApp(item = ItemNavigation.ORDER_DETAIL.name)
    data object IncrementMoreItems : RouteApp(item = ItemNavigation.INCREMENT_MORE_ITEMS.name)
    data object IncrementMoreReservations : RouteApp(item = ItemNavigation.INCREMENT_MORE_RESERVATIONS.name)
    data object ObjectDetail : RouteApp(item = ItemNavigation.OBJECT_DETAIL.name)
    data object Settings : RouteApp(item = ItemNavigation.SETTINGS.name)
}