package br.com.digital.order.navigation

import androidx.navigation.NavHostController

fun navigateBetweenMainRoutes(
    navGraph: NavHostController,
    route: Pair<String, Long?>
) {
    navigateToDetailsOrderScreen(navGraph = navGraph, route = route)
}

private fun navigateToDetailsOrderScreen(
    navGraph: NavHostController,
    route: Pair<String, Long?>
) {
    when (route.first) {
        RouteApp.DetailsDelivery.item -> {
            navGraph.navigate(route = RouteApp.DetailsDelivery.item)
        }

        RouteApp.DetailsOrder.item -> {
            navGraph.navigate(route = RouteApp.DetailsOrder.item)
        }

        RouteApp.DetailsReservation.item -> {
            navGraph.navigate(route = RouteApp.DetailsReservation.item)
        }
    }
}
