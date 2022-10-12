package com.rafaelmfer.weatherforecast.data

import com.rafaelmfer.weatherforecast.TestHelper
import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.data.repository.State
import com.rafaelmfer.weatherforecast.domain.repository.IWeatherForecastRepository
import kotlin.reflect.KClass

// Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeWeatherForecastRepository : IWeatherForecastRepository {

    companion object {
        const val FORECAST_NOT_FOUND = "Forecast could not found"
        const val CITY_NOT_FOUND = "City could not found"
        val forecastResponseTest = readJson("forecastMock.json", ForecastResponse::class)
        val searchResponseTest = readJson("search_autocomplete.json", Array<SearchAutoCompleteResponseItem>::class).toList()


        private fun <T : Any> readJson(fileName: String, clazz: KClass<T>): T {
            val jsonString = TestHelper.loadJsonAsString(fileName)
            return TestHelper.convertJsonToModel(jsonString, clazz.java)
        }
    }

    private var shouldReturnError = false

    fun shouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getForecast(query: String): State<out ForecastResponse> {
        if (shouldReturnError) {
            return State.Error(FORECAST_NOT_FOUND)
        }

        return State.Success(forecastResponseTest)
    }

    override suspend fun searchCities(query: String): State<out List<SearchAutoCompleteResponseItem>> {
        if (shouldReturnError) {
            return State.Error(CITY_NOT_FOUND)
        }

        return State.Success(searchResponseTest)
    }
}