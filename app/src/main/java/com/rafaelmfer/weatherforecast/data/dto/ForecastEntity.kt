package com.rafaelmfer.weatherforecast.data.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable model class for a Forecast. In order to compile with Room
 */

@Entity(tableName = "forecast_table_name")
data class ForecastEntity(
    @PrimaryKey val name: String,
    @Embedded(prefix = "location") val location: LocationEntity,
    @Embedded(prefix = "current") val current: CurrentEntity,
    @Embedded(prefix = "forecasts") val forecasts: List<ForecastDayEntity>
)

data class LocationEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "localtime") val localtime: String
)

data class CurrentEntity(
    @ColumnInfo(name = "tempF") val tempF: Double,
    @Embedded(prefix = "condition") val condition: ConditionEntity,
    @ColumnInfo(name = "windMph") val windMph: Double,
    @ColumnInfo(name = "humidity") val humidity: Int
)

data class ForecastDayEntity(
    @ColumnInfo(name = "maxTempF") val maxTempF: Double,
    @ColumnInfo(name = "minTempF") val minTempF: Double,
    @Embedded(prefix = "condition") val condition: ConditionEntity
)

data class ConditionEntity(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "icon") val icon: String
)


