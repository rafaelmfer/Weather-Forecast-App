package com.rafaelmfer.weatherforecast.presentation

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rafaelmfer.weatherforecast.data.FakeWeatherForecastRepository
import com.rafaelmfer.weatherforecast.data.repository.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import mobi.pulsus.challenge.MainCoroutineRule
import mobi.pulsus.challenge.getOrAwaitValue
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class HomeWeatherForecastViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var weatherForecastRepository: FakeWeatherForecastRepository

    // Class under test
    private lateinit var homeWeatherForecastViewModel: HomeWeatherForecastViewModel

    @Before
    fun setUp() {
        weatherForecastRepository = FakeWeatherForecastRepository()
        homeWeatherForecastViewModel = HomeWeatherForecastViewModel(weatherForecastRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `get forecast _ show loading and then success`() {
        mainCoroutineRule.runBlockingTest {
            //GIVEN
            val keyword = "San Francisco"

            //WHEN
            mainCoroutineRule.pauseDispatcher()
            homeWeatherForecastViewModel.getForecast(keyword)

            //THEN
            MatcherAssert.assertThat(homeWeatherForecastViewModel.forecastLiveData.getOrAwaitValue(), `is`(State.Loading))
            mainCoroutineRule.resumeDispatcher()
            MatcherAssert.assertThat(
                homeWeatherForecastViewModel.forecastLiveData.value, `is`(State.Success(FakeWeatherForecastRepository.forecastResponseTest))
            )
        }
    }

    @Test
    fun `get forecast _ show loading and then error`() {
        mainCoroutineRule.runBlockingTest {
            //GIVEN
            val keyword = "San Francisco"
            weatherForecastRepository.shouldReturnError(true)

            //WHEN
            mainCoroutineRule.pauseDispatcher()
            homeWeatherForecastViewModel.getForecast(keyword)

            //THEN
            MatcherAssert.assertThat(homeWeatherForecastViewModel.forecastLiveData.getOrAwaitValue(), `is`(State.Loading))
            mainCoroutineRule.resumeDispatcher()
            MatcherAssert.assertThat(
                homeWeatherForecastViewModel.forecastLiveData.value, `is`(State.Error(FakeWeatherForecastRepository.FORECAST_NOT_FOUND))
            )
        }
    }

    @Test
    fun `search cities _ show success`() {
        mainCoroutineRule.runBlockingTest {
            //GIVEN
            val keyword = "San Francisco"

            //WHEN
            mainCoroutineRule.pauseDispatcher()
            homeWeatherForecastViewModel.searchCities(keyword)

            //THEN
            MatcherAssert.assertThat(homeWeatherForecastViewModel.citiesLiveData.getOrAwaitValue(), `is`(State.Loading))
            mainCoroutineRule.resumeDispatcher()
            MatcherAssert.assertThat(
                homeWeatherForecastViewModel.citiesLiveData.value, `is`(State.Success(FakeWeatherForecastRepository.searchResponseTest))
            )
        }
    }

    @Test
    fun `search cities _ show loading and then error`() {
        mainCoroutineRule.runBlockingTest {
            //GIVEN
            val keyword = "San Francisco"
            weatherForecastRepository.shouldReturnError(true)

            //WHEN
            mainCoroutineRule.pauseDispatcher()
            homeWeatherForecastViewModel.searchCities(keyword)

            //THEN
            MatcherAssert.assertThat(homeWeatherForecastViewModel.citiesLiveData.getOrAwaitValue(), `is`(State.Loading))
            mainCoroutineRule.resumeDispatcher()
            MatcherAssert.assertThat(
                homeWeatherForecastViewModel.citiesLiveData.value, `is`(State.Error(FakeWeatherForecastRepository.CITY_NOT_FOUND))
            )
        }
    }
}