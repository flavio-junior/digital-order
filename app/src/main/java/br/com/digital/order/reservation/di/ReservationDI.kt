package br.com.digital.order.reservation.di

import br.com.digital.order.reservation.data.repository.ReservationRemoteDataSourceAPI
import br.com.digital.order.reservation.data.repository.ReservationRemoteImplDataSource
import br.com.digital.order.reservation.data.repository.ReservationRepository
import br.com.digital.order.reservation.ui.viewmodel.ReservationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val reservationModule = module {
    factory { ReservationRepository(androidContext(), get()) }
    factory { ReservationRemoteImplDataSource(get()) }
    single { get<Retrofit>().create(ReservationRemoteDataSourceAPI::class.java) }
    viewModel { ReservationViewModel(get()) }
}
