package com.rafaelmfer.weatherforecast.data.repository

import com.rafaelmfer.weatherforecast.data.remote.api.IWeatherForecastApi
import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.domain.repository.IWeatherForecastRepository

/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class State<T : Any> {
    object Loading : State<Nothing>()
    data class Success<T : Any>(val model: T) : State<T>()
    data class Error(val message: String?) : State<Nothing>()
}

class WeatherForecastRepository(
    private val iWeatherForecastApi: IWeatherForecastApi,
) : IWeatherForecastRepository {

    override suspend fun getForecast(query: String): State<out ForecastResponse> {
        return iWeatherForecastApi.getForecast(query = query).run {
            if (isSuccessful) {
                body()?.let {
                    State.Success(it)
                } ?: State.Error(message())
            } else {
                State.Error(message())
            }
        }
    }

    override suspend fun searchCities(query: String): State<out List<SearchAutoCompleteResponseItem>> {
        return iWeatherForecastApi.searchCities(query = query).run {
            if (isSuccessful) {
                body()?.let {
                    State.Success(it)
                } ?: State.Error(message())
            } else {
                State.Error(message())
            }
        }
    }
}