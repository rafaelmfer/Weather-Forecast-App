package com.rafaelmfer.weatherforecast.data.repository

import com.rafaelmfer.weatherforecast.data.remote.api.IWeatherForecastApi
import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.domain.repository.IWeatherForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        return withContext(Dispatchers.IO) {
            try {
                iWeatherForecastApi.getForecast(query = query).run {
                    body()?.let {
                        State.Success(it)
                    } ?: State.Error(message())
                }
            } catch (ex: Exception) {
                State.Error(ex.localizedMessage)
            }
        }
    }

    override suspend fun searchCities(query: String): State<out List<SearchAutoCompleteResponseItem>> {
        return withContext(Dispatchers.IO) {
            try {
                iWeatherForecastApi.searchCities(query = query).run {
                    body()?.let {
                        State.Success(it)
                    } ?: State.Error(message())
                }
            } catch (ex: Exception) {
                State.Error(ex.localizedMessage)
            }
        }
    }
}