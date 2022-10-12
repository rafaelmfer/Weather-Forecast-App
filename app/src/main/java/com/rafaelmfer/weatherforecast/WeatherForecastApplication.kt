package com.rafaelmfer.weatherforecast

import android.app.Application
import com.rafaelmfer.weatherforecast.di.DatabaseModule
import com.rafaelmfer.weatherforecast.di.NetworkModule
import com.rafaelmfer.weatherforecast.di.PresentationModule
import com.rafaelmfer.weatherforecast.di.RepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherForecastApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherForecastApplication)
            modules(
                PresentationModule.module,
                DatabaseModule.module,
                RepositoryModule.module,
                NetworkModule.module,
            )
        }
    }
}