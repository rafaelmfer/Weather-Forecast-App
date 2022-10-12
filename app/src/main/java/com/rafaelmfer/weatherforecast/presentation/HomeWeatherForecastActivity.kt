package com.rafaelmfer.weatherforecast.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.rafaelmfer.weatherforecast.R
import com.rafaelmfer.weatherforecast.customviews.WeatherMinMaxByDayView
import com.rafaelmfer.weatherforecast.data.repository.State
import com.rafaelmfer.weatherforecast.databinding.ActivityHomeWeatherForecastBinding
import com.rafaelmfer.weatherforecast.domain.model.CurrentModel
import com.rafaelmfer.weatherforecast.domain.model.ForecastDayModel
import com.rafaelmfer.weatherforecast.domain.model.ForecastModel
import com.rafaelmfer.weatherforecast.domain.model.SearchAutoCompleteModelItem
import com.rafaelmfer.weatherforecast.extensions.get12hoursTime
import com.rafaelmfer.weatherforecast.extensions.getDayOfWeekWithFullDate
import com.rafaelmfer.weatherforecast.extensions.gone
import com.rafaelmfer.weatherforecast.extensions.hideKeyboard
import com.rafaelmfer.weatherforecast.extensions.onSingleClick
import com.rafaelmfer.weatherforecast.extensions.sectionTextBold
import com.rafaelmfer.weatherforecast.extensions.toSpannableStringBuilder
import com.rafaelmfer.weatherforecast.extensions.viewBinding
import com.rafaelmfer.weatherforecast.extensions.visible
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import java.util.concurrent.TimeUnit

class HomeWeatherForecastActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeWeatherForecastViewModel>()
    private val binding by viewBinding(ActivityHomeWeatherForecastBinding::inflate)

    private val cityAdapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.onViewCreated()
    }

    private fun ActivityHomeWeatherForecastBinding.onViewCreated() {
        observables()
        setupSearchBoxContainer()
    }

    private fun ActivityHomeWeatherForecastBinding.observables() {
        viewModel.run {
            forecastLiveData.observe(this@HomeWeatherForecastActivity) {
                handlerForecastState(it)
            }

            citiesLiveData.observe(this@HomeWeatherForecastActivity) {
                handlerSearchState(it)
            }
        }
    }

    private fun ActivityHomeWeatherForecastBinding.handlerForecastState(state: State<out ForecastModel>) {
        when (state) {
            is State.Loading -> {
                pbHomeWeatherForecast.visible
                groupHomeWeatherForecast.gone
            }
            is State.Success -> {
                pbHomeWeatherForecast.gone
                groupHomeWeatherForecast.visible

                with(state.model.location) {
                    tvHomeClock.text = get12hoursTime(localtime)
                    tvHomeCity.text = name
                    tvHomeTodayDate.text = getDayOfWeekWithFullDate(localtime)
                }

                loadWeatherTodayInfo(state.model.current)

                with(state.model) {
                    wmmToday.setupWeatherImageAndMinMaxTemp(forecasts[0])
                    wmmTomorrow.setupWeatherImageAndMinMaxTemp(forecasts[1])
                    wmmAfterTomorrow.setupWeatherImageAndMinMaxTemp(forecasts[2])
                }
            }
            is State.Error -> {
                pbHomeWeatherForecast.gone
                groupHomeWeatherForecast.gone
            }
        }
    }

    private fun ActivityHomeWeatherForecastBinding.loadWeatherTodayInfo(current: CurrentModel) {
        wtiHome.apply {
            setWeatherImage("${getString(R.string.protocol_https)}${current.condition.icon}")
            val tempF = current.tempF.toString()
            setTemperature(getString(R.string.fahrenheit, tempF).toSpannableStringBuilder().sectionTextBold(tempF))
            setCitation(getString(R.string.weather_citation, current.condition.text.lowercase(Locale.getDefault())))
            setForecastWindValue(getString(R.string.wind_unit, current.windMph.toString()))
            setForecastDropletValue("${current.humidity}%")
        }
    }

    private fun WeatherMinMaxByDayView.setupWeatherImageAndMinMaxTemp(day: ForecastDayModel) {
        apply {
            setWeatherImage("${context.getString(R.string.protocol_https)}${day.condition.icon}")
            setWeatherMinMaxTemperature(day.minTempF.toString(), day.maxTempF.toString())
        }
    }

    private fun ActivityHomeWeatherForecastBinding.handlerSearchState(state: State<out List<SearchAutoCompleteModelItem>>) {
        when (state) {
            is State.Loading -> {
                pbSearchBox.visible
            }
            is State.Success -> {
                pbSearchBox.gone
                rvSearchCities.visibility = if (state.model.isEmpty()) View.GONE else View.VISIBLE
                cityAdapter.apply {
                    updateCityList(state.model)
                    setActionListener { city ->
                        viewModel.getForecast(city)
                        rvSearchCities.gone
                        mbtSearchBoxCollapse.gone
                        hideKeyboard()
                        hideSearchBox()
                    }
                }
            }
            is State.Error -> {
                pbSearchBox.gone
            }
        }
    }

    private fun clearCityList() {
        cityAdapter.updateCityList(listOf())
    }

    private fun ActivityHomeWeatherForecastBinding.setupSearchBoxContainer() {
        mbtHomeSearch.onSingleClick {
            clSearch.visible
            showSearchBox()
        }

        mbtBackSearch.onSingleClick {
            hideSearchBox()
        }

        Observable.create<String> { emitter ->
            tieSearchBox.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    emitter.onNext(it.toString())
                }
            }
        }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.searchCities(it)
            }

        rvSearchCities.adapter = cityAdapter
        mbtSearchBoxCollapse.apply {
            visibility = if (cityAdapter.itemCount == 0) View.GONE else View.VISIBLE
            onSingleClick {
                rvSearchCities.gone
                it.gone
            }
        }
    }

    private fun ActivityHomeWeatherForecastBinding.hideSearchBox() {
        clSearch.animate()
            .translationY(-clSearch.height.toFloat())
            .alpha(0F)
            .setDuration(500L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    clSearch.animate().setListener(null)
                    clSearch.gone
                }
            })
            .start()

        tieSearchBox.setText("")
        clearCityList()
    }

    private fun ActivityHomeWeatherForecastBinding.showSearchBox() {
        clSearch.animate()
            .translationY(0F)
            .alpha(1F)
            .setDuration(500L)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    clSearch.animate().setListener(null)
                }
            })
            .start()
    }
}