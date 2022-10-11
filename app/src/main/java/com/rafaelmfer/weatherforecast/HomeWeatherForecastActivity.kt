package com.rafaelmfer.weatherforecast

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.rafaelmfer.weatherforecast.data.remote.response.ForecastResponse
import com.rafaelmfer.weatherforecast.data.remote.response.SearchAutoCompleteResponseItem
import com.rafaelmfer.weatherforecast.data.repository.State
import com.rafaelmfer.weatherforecast.databinding.ActivityMainBinding
import com.rafaelmfer.weatherforecast.databinding.IncludeWeatherMinMaxByDayBinding
import com.rafaelmfer.weatherforecast.presentation.HomeWeatherForecastViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

class HomeWeatherForecastActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeWeatherForecastViewModel>()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val cityAdapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.onViewCreated()
    }

    private fun ActivityMainBinding.onViewCreated() {
        observables()
        setupSearchBoxContainer()
        mbtHomeSearch.onSingleClick {
            clSearch.visible
            showSearchBox()
        }

        mbtBackSearch.onSingleClick {
            hideSearchBox()
        }
    }

    private fun ActivityMainBinding.observables() {
        viewModel.run {
            forecastLiveData.observe(this@HomeWeatherForecastActivity) {
                handlerForecastState(it)
            }

            citiesLiveData.observe(this@HomeWeatherForecastActivity) {
                handlerSearchState(it)
            }
        }
    }

    private fun ActivityMainBinding.handlerForecastState(state: State<out ForecastResponse>) {
        when (state) {
            is State.Loading -> {
                pbHomeWeatherForecast.visible
                groupHomeWeatherForecast.gone
            }
            is State.Success -> {
                pbHomeWeatherForecast.gone
                groupHomeWeatherForecast.visible
                val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
                val outputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
                val time = inputFormat.parse(state.model.location.localtime)
                time?.let { tvHomeClock.text = outputFormat.format(it) }
                tvHomeCity.text = state.model.location.name
                val date = LocalDate.parse(state.model.forecast.forecastDay[0].date)
                val dayOfWeek = date.dayOfWeek.toString()
                    .lowercase(Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                tvHomeTodayDate.text = "$dayOfWeek, ${date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))}"
                incHomeWeatherTodayInformation.apply {
                    Glide.with(this@HomeWeatherForecastActivity)
                        .load("${getString(R.string.protocol_https)}${state.model.current.condition.icon}")
                        .into(ivHomeWeatherTodayImage)
                    val tempF = state.model.current.tempF.toString()
                    tvHomeWeatherTodayTemperature.text = getString(R.string.fahrenheit, tempF).toSpannableStringBuilder().sectionTextBold(tempF)
                    tvHomeWeatherTodayCitation.text = getString(R.string.weather_citation, state.model.current.condition.text.lowercase(Locale.getDefault()))
                    incWeatherTodayInformationWind.tvForecastInformationText.text = getString(R.string.wind_unit, state.model.current.windMph.toString())
                    incWeatherTodayInformationDroplet.apply {
                        ivForecastInformationImage.setImageResource(R.drawable.ic_droplet)
                        tvForecastInformationText.text = "${state.model.current.humidity}%"
                    }
                }
                incWeatherMinMaxToday.weatherMinMaxByDay(
                    state.model.forecast.forecastDay[0].day.condition.icon,
                    state.model.forecast.forecastDay[0].day.minTempF,
                    state.model.forecast.forecastDay[0].day.maxTempF,
                    getString(R.string.today)
                )
                incWeatherMinMaxTomorrow.weatherMinMaxByDay(
                    state.model.forecast.forecastDay[1].day.condition.icon,
                    state.model.forecast.forecastDay[1].day.minTempF,
                    state.model.forecast.forecastDay[1].day.maxTempF,
                    getString(R.string.tomorrow)
                )
                incWeatherMinMaxAfterTomorrow.weatherMinMaxByDay(
                    state.model.forecast.forecastDay[2].day.condition.icon,
                    state.model.forecast.forecastDay[2].day.minTempF,
                    state.model.forecast.forecastDay[2].day.maxTempF,
                    getString(R.string.friday)
                )
            }
            is State.Error -> {
                pbHomeWeatherForecast.gone
                groupHomeWeatherForecast.gone
            }
        }
    }

    private fun IncludeWeatherMinMaxByDayBinding.weatherMinMaxByDay(image: String, minTemp: Double, maxTemp: Double, day: String) {
        apply {
            Glide.with(this@HomeWeatherForecastActivity)
                .load("${getString(R.string.protocol_https)}$image")
                .into(ivWeatherMinMaxByDayWeatherImage)
            tvWeatherMinMaxByDayMinMax.text = getString(R.string.temperature_min_max, minTemp, maxTemp)
            tvWeatherMinMaxByDayDay.text = day
        }
    }

    private fun ActivityMainBinding.handlerSearchState(state: State<out List<SearchAutoCompleteResponseItem>>) {
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

    private fun ActivityMainBinding.setupSearchBoxContainer() {
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

    private fun ActivityMainBinding.hideSearchBox() {
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

    private fun ActivityMainBinding.showSearchBox() {
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