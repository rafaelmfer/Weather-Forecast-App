package com.rafaelmfer.weatherforecast.di

import com.rafaelmfer.weatherforecast.data.network.HTTPClient
import com.rafaelmfer.weatherforecast.data.remote.api.IWeatherForecastApi
import org.koin.dsl.module

object NetworkModule {
    val module = module {
        single { HTTPClient() }
        factory { get<HTTPClient>().create(IWeatherForecastApi::class) }
    }
}