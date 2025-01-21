package br.com.digital.order.category.di

import br.com.digital.order.category.domain.ConverterCategory
import org.koin.dsl.module

val categoryModule = module {
    single { ConverterCategory() }
}
