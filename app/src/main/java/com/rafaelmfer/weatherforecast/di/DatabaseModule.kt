package com.rafaelmfer.weatherforecast.di

import com.rafaelmfer.weatherforecast.data.local.LocalDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DatabaseModule {
    val module = module {
        single { LocalDB.createForecastDao(androidContext()) }
    }
}