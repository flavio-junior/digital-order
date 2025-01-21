package br.com.digital.order.dashboard.di

import br.com.digital.order.dashboard.ui.viewmodel.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel { DashboardViewModel(get()) }
}