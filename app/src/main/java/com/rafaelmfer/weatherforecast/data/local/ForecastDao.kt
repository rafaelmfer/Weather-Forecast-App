package com.rafaelmfer.weatherforecast.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelmfer.weatherforecast.data.dto.ForecastEntity

/**
 * Data Access Object for the forecast table.
 */
@Dao
interface ForecastDao {
    /**
     * @return all forecast.
     */
    @Query("SELECT * FROM forecast_table_name")
    suspend fun getAllForecasts(): List<ForecastEntity>

    /**
     * @return a forecast by the location's name.
     *
     * @param name the location's name of the forecast.
     */
    @Query("SELECT * FROM forecast_table_name WHERE name = :name")
    fun getForecastByName(name: String): ForecastEntity?

    /**
     * Insert a forecast in the database. If the forecast already exists, replace it.
     *
     * @param forecast the joke to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveForecast(forecast: ForecastEntity)

    /**
     * Delete all forecast.
     */
    @Query("DELETE FROM forecast_table_name")
    suspend fun deleteAllForecasts()
}