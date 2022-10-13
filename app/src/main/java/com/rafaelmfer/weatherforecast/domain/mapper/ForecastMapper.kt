package com.rafaelmfer.weatherforecast.domain.mapper

import com.rafaelmfer.weatherforecast.data.remote.response.ForecastAPIResponse
import com.rafaelmfer.weatherforecast.domain.model.ConditionModel
import com.rafaelmfer.weatherforecast.domain.model.CurrentModel
import com.rafaelmfer.weatherforecast.domain.model.ForecastDayModel
import com.rafaelmfer.weatherforecast.domain.model.ForecastModel
import com.rafaelmfer.weatherforecast.domain.model.LocationModel

fun ForecastAPIResponse.asDomainModel(): ForecastModel {
    return ForecastModel(
        location = LocationModel(
            name = location.name,
            localtime = location.localtime
        ),
        current = CurrentModel(
            tempF = current.tempF,
            condition = ConditionModel(
                text = current.condition.text,
                icon = current.condition.icon
            ),
            windMph = current.windMph,
            humidity = current.humidity
        ),
        forecasts = forecast.forecastDay.map {
            ForecastDayModel(
                maxTempF = it.day.maxTempF,
                minTempF = it.day.minTempF,
                condition = ConditionModel(
                    text = it.day.condition.text,
                    icon = it.day.condition.icon
                )
            )
        }
    )
}