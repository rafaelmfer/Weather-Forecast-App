package com.rafaelmfer.weatherforecast.domain.repository

import com.rafaelmfer.weatherforecast.data.dto.ForecastEntity
import com.rafaelmfer.weatherforecast.data.repository.State
import com.rafaelmfer.weatherforecast.domain.model.ForecastModel
import com.rafaelmfer.weatherforecast.domain.model.SearchAutoCompleteModelItem

interface IWeatherForecastRepository {
    suspend fun getForecast(query: String): State<out ForecastModel>
    suspend fun searchCities(query: String): State<out List<SearchAutoCompleteModelItem>>
    suspend fun getLastForecast(): ForecastEntity?
    suspend fun getForecastByName(cityName: String): ForecastEntity?
    suspend fun saveForecast(forecast: ForecastEntity)
    suspend fun deleteAllForecasts()
}