package br.com.digital.order.navigation

import androidx.navigation.NavHostController
import br.com.digital.order.networking.resources.AlternativesRoutes

fun goToNextScreen(
    navHostController: NavHostController,
    currentScreen: String,
    nextScreen: String
) {
    navHostController.navigate(route = nextScreen) {
        popUpTo(currentScreen) {
            inclusive = true
        }
    }
}

fun navigateToAlternativeRoutes(
    navController: NavHostController,
    currentScreen: String,
    alternativeRoutes: AlternativesRoutes?
) {
    when (alternativeRoutes) {
        AlternativesRoutes.ERROR_403 -> {
            navController.navigate(route = RouteApp.SignIn.item) {
                popUpTo(currentScreen) {
                    inclusive = true
                }
            }
        }

        else -> {

        }
    }
}
