package com.rafaelmfer.weatherforecast.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.rafaelmfer.weatherforecast.R
import com.rafaelmfer.weatherforecast.databinding.ViewWeatherMinMaxByDayBinding

class WeatherMinMaxByDayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewWeatherMinMaxByDayBinding.inflate(LayoutInflater.from(context), this, true)

    private var attributesTypedArray: TypedArray

    init {
        attributesTypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.WeatherMinMaxByDayView, defStyleAttr, 0).apply {
            binding.apply {
                tvWeatherMinMaxByDayMinMax.text = getString(R.styleable.WeatherMinMaxByDayView_text)
                tvWeatherMinMaxByDayDay.text = getString(R.styleable.WeatherMinMaxByDayView_dayOfWeek)
            }
            recycle()
        }
    }

    fun setWeatherImage(urlImage: String) {
        Glide.with(context)
            .load(urlImage)
            .into(binding.ivWeatherMinMaxByDayWeatherImage)
    }

    fun setWeatherMinMaxTemperature(minTemperature: String, maxTemperature: String) {
        binding.tvWeatherMinMaxByDayMinMax.text = context.getString(R.string.min_temp_max_temp, minTemperature, maxTemperature)
    }
}