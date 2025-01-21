package br.com.digital.order.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.digital.order.account.ui.view.SignInScreen
import br.com.digital.order.dashboard.ui.view.DashboardScreen
import br.com.digital.order.dashboard.ui.view.DeliveryScreen
import br.com.digital.order.dashboard.ui.view.OrderScreen
import br.com.digital.order.dashboard.ui.view.ReservationScreen
import br.com.digital.order.splash.SplashScreen

@Composable
fun NavigationApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouteApp.SplashScreen.item
) {
    NavHost(navController = navController, startDestination = startDestination) {
        splashScreenNavigation(navController = navController)
        signInNavigation(navController = navController)
        dashboardNavigation(navController = navController)
    }
}

private fun NavGraphBuilder.splashScreenNavigation(
    navController: NavHostController
) {
    composable(RouteApp.SplashScreen.item) {
        SplashScreen(
            goToSignInScreen = {
                navController.navigate(route = RouteApp.SignIn.item) {
                    popUpTo(RouteApp.SplashScreen.item) { inclusive = true }
                }
            },
            goToDashboardScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.SplashScreen.item) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}

private fun NavGraphBuilder.signInNavigation(
    navController: NavHostController
) {
    composable(RouteApp.SignIn.item) {
        SignInScreen(
            goToDashboardScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.SignIn.item) {
                        inclusive = true
                    }
                }
            },
            goToSendRecoverTokenScreen = {},
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = RouteApp.SignIn.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}

private fun NavGraphBuilder.dashboardNavigation(
    navController: NavHostController
) {
    composable(RouteApp.Dashboard.item) {
        DashboardScreen(navGraph = navController)
    }
    composable(RouteApp.Delivery.item) {
        DeliveryScreen(
            goToBack = {
                navController.popBackStack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = RouteApp.Delivery.item,
                    alternativeRoutes = it
                )
            }
        )
    }
    composable(RouteApp.Order.item) {
        OrderScreen(
            goToBack = {
                navController.popBackStack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = RouteApp.Order.item,
                    alternativeRoutes = it
                )
            }
        )
    }
    composable(RouteApp.Reservation.item) {
        ReservationScreen(
            goToBack = {
                navController.popBackStack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = RouteApp.Reservation.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}