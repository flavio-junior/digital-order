package br.com.digital.order.dashboard.ui.view

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.digital.order.navigation.DETAILS_ORDER
import br.com.digital.order.navigation.RouteApp
import br.com.digital.order.navigation.navigateArgs

@Composable
fun BottomNavigationOrder(
    modifier: Modifier,
    navController: NavHostController,
    navGraph: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationRoute.PDV.route.name,
        modifier = modifier
    ) {
        composable(BottomNavigationRoute.PDV.route.name) {
            PdvScreen(navGraph = navGraph)
        }

        composable(BottomNavigationRoute.PendingOrders.route.name) {
            PendingOrdersScreen(
                goToNextScreen = {
                    val bundle = Bundle()
                    bundle.putParcelable(DETAILS_ORDER, it.second)
                    navGraph.navigateArgs(route = it.first, args = bundle)
                },
                goToAlternativeRoutes = {}
            )
        }

        composable(BottomNavigationRoute.Settings.route.name) {
            SettingsScreen(
                goToLoginScreen = {
                    navGraph.navigate(route = RouteApp.SignIn.item)
                },
                goToSplashScreen = {
                    navGraph.navigate(route = RouteApp.SplashScreen.item) {
                        popUpTo(RouteApp.Dashboard.item) {
                            inclusive = true
                        }
                    }
                },
                goToAlternativeRoutes = {}
            )
        }
    }
}
