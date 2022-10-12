package com.rafaelmfer.weatherforecast.data.remote.api

import com.rafaelmfer.weatherforecast.BuildConfig
import com.rafaelmfer.weatherforecast.data.remote.response.ForecastAPIResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherForecastApi {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String = BuildConfig.API_KEY_WEATHER_API,
        @Query("q") query: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "yes",
        @Query("alerts") alerts: String = "no",
    ): Response<ForecastAPIResponse>

    @GET("search.json")
    suspend fun searchCities(
        @Query("key") apiKey: String = BuildConfig.API_KEY_WEATHER_API,
        @Query("q") query: String
    ): Response<List<SearchAutoCompleteResponseItem>>
}