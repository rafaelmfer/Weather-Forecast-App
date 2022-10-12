package com.rafaelmfer.weatherforecast.data.repository

import com.rafaelmfer.weatherforecast.data.dto.ForecastEntity
import com.rafaelmfer.weatherforecast.data.local.ForecastDao
import com.rafaelmfer.weatherforecast.data.remote.api.IWeatherForecastApi
import com.rafaelmfer.weatherforecast.domain.mapper.asDomainModel
import com.rafaelmfer.weatherforecast.domain.model.ForecastModel
import com.rafaelmfer.weatherforecast.domain.model.SearchAutoCompleteModelItem
import com.rafaelmfer.weatherforecast.domain.repository.IWeatherForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
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
    private val forecastDao: ForecastDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IWeatherForecastRepository {

    override suspend fun getForecast(query: String): State<out ForecastModel> {
        return withContext(ioDispatcher) {
            try {
                iWeatherForecastApi.getForecast(query = query).run {
                    body()?.let {
                        State.Success(it.asDomainModel())
                    } ?: State.Error(message())
                }
            } catch (ex: Exception) {
                State.Error(ex.localizedMessage)
            }
        }
    }

    override suspend fun searchCities(query: String): State<out List<SearchAutoCompleteModelItem>> {
        return withContext(ioDispatcher) {
            try {
                iWeatherForecastApi.searchCities(query = query).run {
                    body()?.let {
                        State.Success(it.map { item -> item.asDomainModel() })
                    } ?: State.Error(message())
                }
            } catch (ex: Exception) {
                State.Error(ex.localizedMessage)
            }
        }
    }

    override suspend fun getLastForecast(): ForecastEntity? {
        return withContext(ioDispatcher) {
            forecastDao.getAllForecasts().lastOrNull()
        }
    }

    override suspend fun getForecastByName(cityName: String): ForecastEntity? {
        return withContext(ioDispatcher) {
            forecastDao.getForecastByName(cityName)
        }
    }

    override suspend fun saveForecast(forecast: ForecastEntity) {
        withContext(ioDispatcher) {
            forecastDao.saveForecast(forecast)
        }
    }

    override suspend fun deleteAllForecasts() {
        withContext(ioDispatcher) {
            forecastDao.deleteAllForecasts()
        }
    }
}