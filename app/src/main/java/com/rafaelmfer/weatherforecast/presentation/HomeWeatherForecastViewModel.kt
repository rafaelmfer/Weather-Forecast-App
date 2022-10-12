package com.rafaelmfer.weatherforecast.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.data.repository.State
import com.rafaelmfer.weatherforecast.domain.repository.IWeatherForecastRepository
import kotlinx.coroutines.launch

class HomeWeatherForecastViewModel(
    private val repository: IWeatherForecastRepository
) : ViewModel() {

    companion object {
        private const val CITY_DEFAULT = "San Francisco"
    }

    private val forecastMutableLiveData = MutableLiveData<State<out ForecastResponse>>()
    val forecastLiveData: LiveData<State<out ForecastResponse>> get() = forecastMutableLiveData

    private val citiesMutableLiveData = MutableLiveData<State<out List<SearchAutoCompleteResponseItem>>>()
    val citiesLiveData: LiveData<State<out List<SearchAutoCompleteResponseItem>>> get() = citiesMutableLiveData

    init {
        getForecast(CITY_DEFAULT)
    }

    fun getForecast(query: String) {
        forecastMutableLiveData.postValue(State.Loading)
        viewModelScope.launch {
            forecastMutableLiveData.postValue(repository.getForecast(query))
        }
    }

    fun searchCities(query: String) {
        citiesMutableLiveData.postValue(State.Loading)
        viewModelScope.launch {
            citiesMutableLiveData.postValue(repository.searchCities(query))
        }
    }
}