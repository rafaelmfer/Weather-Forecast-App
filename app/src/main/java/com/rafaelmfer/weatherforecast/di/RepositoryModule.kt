package com.rafaelmfer.weatherforecast.di

import com.rafaelmfer.weatherforecast.data.repository.WeatherForecastRepository
import com.rafaelmfer.weatherforecast.domain.repository.IWeatherForecastRepository
import org.koin.dsl.module

object RepositoryModule {
    val module = module {
        factory<IWeatherForecastRepository> { WeatherForecastRepository(get()) }
    }
}