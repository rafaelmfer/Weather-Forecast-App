package com.rafaelmfer.weatherforecast.di

import com.rafaelmfer.weatherforecast.presentation.HomeWeatherForecastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    val module = module {
        viewModel { HomeWeatherForecastViewModel(get()) }
    }
}