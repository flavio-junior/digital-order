package br.com.digital.order.navigation

sealed class RouteApp(val item: String) {
    data object SplashScreen : RouteApp(item = ItemNavigation.SPLASH_SCREEN.name)
    data object SignIn : RouteApp(item = ItemNavigation.SIGN_IN.name)
    data object Dashboard : RouteApp(item = ItemNavigation.DASHBOARD.name)
    data object Delivery : RouteApp(item = ItemNavigation.DELIVERY.name)
    data object Order : RouteApp(item = ItemNavigation.ORDER.name)
    data object DetailsDelivery : RouteApp(item = ItemNavigation.DETAILS_DELIVERY.name)
    data object DetailsOrder : RouteApp(item = ItemNavigation.DETAILS_ORDER.name)
    data object DetailsReservation : RouteApp(item = ItemNavigation.DETAILS_RESERVATION.name)
    data object Reservation : RouteApp(item = ItemNavigation.RESERVATION.name)
    data object Settings : RouteApp(item = ItemNavigation.SETTINGS.name)
}