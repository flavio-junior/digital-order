package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.digital.order.ui.theme.Themes

@Composable
fun DashboardScreen(
    navController: NavHostController = rememberNavController(),
    navGraph: NavHostController
) {
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .padding(all = Themes.size.spaceSize8)
            ) {
                BottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        BottomNavigationOrder(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            navGraph = navGraph
        )
    }
}
