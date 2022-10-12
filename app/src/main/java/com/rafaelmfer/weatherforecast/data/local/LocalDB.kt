package com.rafaelmfer.weatherforecast.data.local

import android.content.Context
import androidx.room.Room

/**
 * Singleton class that is used to create a reminder db
 */
object LocalDB {

    /**
     * static method that creates a weather forecast class and returns the DAO of the forecast
     */
    fun createForecastDao(context: Context): ForecastDao {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherForecastDatabase::class.java,
            "weather_forecast.db"
        )
            .build()
            .forecastDao()
    }
}