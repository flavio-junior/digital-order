package br.com.digital.order.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
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

const val DETAILS_ORDER = "DETAILS_ORDER"
const val ORDER_ID_ARG = "ORDER_ID_ARG"
const val OBJECT_ARG = "OBJECT_ARG"

fun NavController.navigateArgs(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val nodeId = graph.findNode(route = route)?.id
    if (nodeId != null) {
        val options = navOptions ?: NavOptions.Builder().build()
        navigate(nodeId, args, options, navigatorExtras)
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
