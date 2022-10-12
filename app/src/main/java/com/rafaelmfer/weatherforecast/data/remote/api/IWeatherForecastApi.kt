package com.rafaelmfer.weatherforecast.data.remote.api

import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "baffc336659a48b2bb112156221110"

interface IWeatherForecastApi {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String = API_KEY,
        @Query("q") query: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alerts: String = "no",
    ): Response<ForecastResponse>

    @GET("search.json")
    suspend fun searchCities(
        @Query("key") apiKey: String = API_KEY,
        @Query("q") query: String
    ): Response<List<SearchAutoCompleteResponseItem>>
}