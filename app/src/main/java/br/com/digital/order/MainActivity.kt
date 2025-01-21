package br.com.digital.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.digital.order.navigation.NavigationApp
import br.com.digital.order.ui.theme.Theme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Theme {
                        NavigationApp(navController = rememberNavController())
                    }
                }
            }
        }
    }
}
