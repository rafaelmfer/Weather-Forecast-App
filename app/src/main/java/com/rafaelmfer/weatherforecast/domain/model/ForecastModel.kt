package com.rafaelmfer.weatherforecast.domain.model

data class ForecastModel(
    val location: LocationModel,
    val current: CurrentModel,
    val forecasts: List<ForecastDayModel>
)

data class LocationModel(
    val name: String,
    val localtime: String
)

data class CurrentModel(
    val tempF: Double,
    val condition: ConditionModel,
    val windMph: Double,
    val humidity: Int
)

data class ForecastDayModel(
    val maxTempF: Double,
    val minTempF: Double,
    val condition: ConditionModel
)

data class ConditionModel(
    val text: String,
    val icon: String
)


