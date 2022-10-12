package com.rafaelmfer.weatherforecast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rafaelmfer.weatherforecast.data.dto.ForecastEntity

/**
 * The Room Database that contains the forecast table.
 */
@Database(entities = [ForecastEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherForecastDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
}