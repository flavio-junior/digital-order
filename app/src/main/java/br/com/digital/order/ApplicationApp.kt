package br.com.digital.order

import android.app.Application
import br.com.digital.order.account.di.androidModule
import br.com.digital.order.category.di.categoryModule
import br.com.digital.order.common.di.commonModule
import br.com.digital.order.dashboard.di.dashboardModule
import br.com.digital.order.food.di.foodModule
import br.com.digital.order.item.di.itemModule
import br.com.digital.order.networking.di.networkModule
import br.com.digital.order.product.di.productModule
import br.com.digital.order.reservation.di.reservationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationApp)
            androidLogger(Level.INFO)
            modules(
                listOf(
                    androidModule,
                    categoryModule,
                    commonModule,
                    dashboardModule,
                    foodModule,
                    itemModule,
                    networkModule,
                    productModule,
                    reservationModule
                )
            )
        }
    }
}
