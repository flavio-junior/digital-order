package br.com.digital.order.navigation

import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.digital.order.account.ui.view.SignInScreen
import br.com.digital.order.dashboard.ui.view.DashboardScreen
import br.com.digital.order.dashboard.ui.view.DeliveryScreen
import br.com.digital.order.dashboard.ui.view.OrderScreen
import br.com.digital.order.dashboard.ui.view.ReservationScreen
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.order.ui.view.DetailsObjectScreen
import br.com.digital.order.order.ui.view.IncrementMoreItemsScreen
import br.com.digital.order.order.ui.view.IncrementMoreReservationsScreen
import br.com.digital.order.order.ui.view.OrderDetailsScreen
import br.com.digital.order.reservation.data.vo.ObjectResponseVO
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
    composable(route = RouteApp.SplashScreen.item) {
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
    composable(route = RouteApp.SignIn.item) {
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
    composable(route = RouteApp.Dashboard.item) {
        DashboardScreen(navGraph = navController)
    }
    composable(route = RouteApp.Delivery.item) {
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

    composable(route = RouteApp.Order.item) {
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

    composable(route = RouteApp.Reservation.item) {
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

    composable(route = RouteApp.OrderDetail.item) { parameter ->
        val bundle = parameter.arguments
        val orderResponseVO = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable(DETAILS_ORDER, OrderResponseVO::class.java)
        } else {
            bundle?.getParcelable(DETAILS_ORDER) as OrderResponseVO?
        }
        OrderDetailsScreen(
            orderDetailsResponseVO = orderResponseVO,
            goToBack = {
                navController.popBackStack()
            },
            objectResponseVO = { objectSelected ->
                val objectBundle = Bundle()
                objectBundle.putParcelable(OBJECT_ARG, objectSelected.first)
                objectBundle.putLong(ORDER_ID_ARG, objectSelected.second)
                navController.navigateWithArgsAndBundle(
                    route = RouteApp.ObjectDetail.item,
                    args = objectBundle,
                    argumentKey = ORDER_ID_ARG,
                    argumentValue = objectSelected.second
                )
            },
            goToNextScreen = {
                navController.navigate(route = "${it.first}?$ORDER_ID_ARG=${it.second}")
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = RouteApp.OrderDetail.item,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable(
        route = "${RouteApp.IncrementMoreReservations.item}?$ORDER_ID_ARG={$ORDER_ID_ARG}",
        arguments = listOf(navArgument(ORDER_ID_ARG) { type = NavType.LongType })
    ) { parameter ->
        IncrementMoreReservationsScreen(
            orderId = parameter.arguments?.getLong(ORDER_ID_ARG),
            goToBack = {
                navController.popBackStack()
            },
            goToNextScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.IncrementMoreReservations.item) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }

    composable(
        route = "${RouteApp.IncrementMoreItems.item}?$ORDER_ID_ARG={$ORDER_ID_ARG}",
        arguments = listOf(navArgument(ORDER_ID_ARG) { type = NavType.LongType })
    ) { parameter ->
        IncrementMoreItemsScreen(
            orderId = parameter.arguments?.getLong(ORDER_ID_ARG),
            goToBack = {
                navController.popBackStack()
            },
            goToNextScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.IncrementMoreItems.item) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }

    composable(route = RouteApp.ObjectDetail.item) { parameter ->
        val bundle = parameter.arguments
        val objectResponseVO = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getParcelable(OBJECT_ARG, ObjectResponseVO::class.java)
        } else {
            bundle?.getParcelable(OBJECT_ARG) as ObjectResponseVO?
        }
        val orderId = bundle?.getLong(ORDER_ID_ARG)
        DetailsObjectScreen(
            orderId = orderId,
            objectResponseVO = objectResponseVO,
            goToBack = {
                navController.popBackStack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(RouteApp.ObjectDetail.item) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }
}
