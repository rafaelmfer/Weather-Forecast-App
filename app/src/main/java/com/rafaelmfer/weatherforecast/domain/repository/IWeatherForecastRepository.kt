package com.rafaelmfer.weatherforecast.domain.repository

import com.rafaelmfer.weatherforecast.data.repository.State
import com.rafaelmfer.weatherforecast.domain.model.ForecastModel
import com.rafaelmfer.weatherforecast.domain.model.SearchAutoCompleteModelItem

interface IWeatherForecastRepository {
    suspend fun getForecast(query: String): State<out ForecastModel>
    suspend fun searchCities(query: String): State<out List<SearchAutoCompleteModelItem>>
}