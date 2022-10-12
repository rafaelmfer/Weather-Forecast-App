package com.rafaelmfer.weatherforecast.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rafaelmfer.weatherforecast.data.dto.ConditionEntity
import com.rafaelmfer.weatherforecast.data.dto.CurrentEntity
import com.rafaelmfer.weatherforecast.data.dto.ForecastDayEntity
import com.rafaelmfer.weatherforecast.data.dto.LocationEntity

class Converters {

    @TypeConverter
    fun listToJson(value: List<ForecastDayEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ForecastDayEntity>::class.java).toList()

    @TypeConverter
    fun locationToString(location: LocationEntity): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun stringToLocation(string: String): LocationEntity? {
        if (string.isEmpty())
            return null
        return Gson().fromJson(string, LocationEntity::class.java)
    }

    @TypeConverter
    fun currentToString(location: CurrentEntity): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun stringToCurrent(string: String): CurrentEntity? {
        if (string.isEmpty())
            return null
        return Gson().fromJson(string, CurrentEntity::class.java)
    }

    @TypeConverter
    fun conditionToString(location: ConditionEntity): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun stringToCondition(string: String): ConditionEntity? {
        if (string.isEmpty())
            return null
        return Gson().fromJson(string, ConditionEntity::class.java)
    }
}