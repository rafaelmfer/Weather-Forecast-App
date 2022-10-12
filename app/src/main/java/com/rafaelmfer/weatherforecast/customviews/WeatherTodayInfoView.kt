package com.rafaelmfer.weatherforecast.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.rafaelmfer.weatherforecast.R
import com.rafaelmfer.weatherforecast.databinding.ViewWeatherTodayInfoBinding

class WeatherTodayInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewWeatherTodayInfoBinding.inflate(LayoutInflater.from(context), this, true)

    private var attributesTypedArray: TypedArray

    init {
        attributesTypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.WeatherTodayInfoView, defStyleAttr, 0).apply {
            binding.apply {
                tvHomeWeatherTodayTemperature.text = getString(R.styleable.WeatherTodayInfoView_temperature)
                tvHomeWeatherTodayCitation.text = getString(R.styleable.WeatherTodayInfoView_citation)
                incWeatherTodayInformationWind.tvForecastInformationText.text = getString(R.styleable.WeatherTodayInfoView_forecastWind)
                incWeatherTodayInformationDroplet.tvForecastInformationText.text = getString(R.styleable.WeatherTodayInfoView_forecastDroplet)
                loadForecastIcons()
            }
            recycle()
        }
    }

    private fun ViewWeatherTodayInfoBinding.loadForecastIcons() {
        incWeatherTodayInformationWind.ivForecastInformationImage.setImageResource(R.drawable.ic_wind)
        incWeatherTodayInformationDroplet.ivForecastInformationImage.setImageResource(R.drawable.ic_droplet)
    }

    fun setWeatherImage(urlImage: String) {
        Glide.with(context)
            .load(urlImage)
            .into(binding.ivHomeWeatherTodayImage)
    }

    fun setTemperature(text: CharSequence) {
        binding.tvHomeWeatherTodayTemperature.text = text
    }

    fun setCitation(text: String) {
        binding.tvHomeWeatherTodayCitation.text = text
    }

    fun setForecastWindValue(text: String) {
        binding.incWeatherTodayInformationWind.tvForecastInformationText.text = text
    }

    fun setForecastDropletValue(text: String) {
        binding.incWeatherTodayInformationDroplet.tvForecastInformationText.text = text
    }
}