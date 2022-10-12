package com.rafaelmfer.weatherforecast.domain.repository

import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.data.repository.State

interface IWeatherForecastRepository {
    suspend fun getForecast(query: String): State<out ForecastResponse>
    suspend fun searchCities(query: String): State<out List<SearchAutoCompleteResponseItem>>
}