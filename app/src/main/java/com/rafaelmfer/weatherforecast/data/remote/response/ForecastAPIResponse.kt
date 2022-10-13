package com.rafaelmfer.weatherforecast.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastAPIResponse(
    @SerializedName("location") val location: LocationResponse,
    @SerializedName("current") val current: CurrentResponse,
    @SerializedName("forecast") val forecast: ForecastResponse
)

data class LocationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("tz_id") val tzId: String,
    @SerializedName("localtime_epoch") val localtimeEpoch: Int,
    @SerializedName("localtime") val localtime: String
)

data class CurrentResponse(
    @SerializedName("last_updated_epoch") val lastUpdatedEpoch: Int,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("condition") val condition: ConditionResponse,
    @SerializedName("wind_mph") val windMph: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDir: String,
    @SerializedName("pressure_mb") val pressureMb: Double,
    @SerializedName("pressure_in") val pressureIn: Double,
    @SerializedName("precip_mm") val precipMm: Double,
    @SerializedName("precip_in") val precipIn: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("feelslike_c") val feelsLikeC: Double,
    @SerializedName("feelslike_f") val feelsLikeF: Double,
    @SerializedName("vis_km") val visKm: Double,
    @SerializedName("vis_miles") val visMiles: Double,
    @SerializedName("uv") val uv: Double,
    @SerializedName("gust_mph") val gustMph: Double,
    @SerializedName("gust_kph") val gustKph: Double,
    @SerializedName("air_quality") val airQuality: AirQualityResponse
)

data class ForecastResponse(
    @SerializedName("forecastday") val forecastDay: List<ForecastDayResponse>
)

data class ForecastDayResponse(
    @SerializedName("date") val date: String,
    @SerializedName("date_epoch") val dateEpoch: Int,
    @SerializedName("day") val day: DayResponse,
    @SerializedName("astro") val astro: AstroResponse,
    @SerializedName("hour") val hour: List<HourResponse>
)

data class DayResponse(
    @SerializedName("maxtemp_c") val maxTempC: Double,
    @SerializedName("maxtemp_f") val maxTempF: Double,
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("mintemp_f") val minTempF: Double,
    @SerializedName("avgtemp_c") val avgTempC: Double,
    @SerializedName("avgtemp_f") val avgTempF: Double,
    @SerializedName("maxwind_mph") val maxWindMph: Double,
    @SerializedName("maxwind_kph") val maxWindKph: Double,
    @SerializedName("totalprecip_mm") val totalPrecipMm: Double,
    @SerializedName("totalprecip_in") val totalPrecipIn: Double,
    @SerializedName("avgvis_km") val avgVisKm: Double,
    @SerializedName("avgvis_miles") val avgVisMiles: Double,
    @SerializedName("avghumidity") val avgHumidity: Double,
    @SerializedName("daily_will_it_rain") val dailyWillItRain: Int,
    @SerializedName("daily_chance_of_rain") val dailyChanceOfRain: Int,
    @SerializedName("daily_will_it_snow") val dailyWillItSnow: Int,
    @SerializedName("daily_chance_of_snow") val dailyChanceOfSnow: Int,
    @SerializedName("condition") val condition: ConditionResponse,
    @SerializedName("uv") val uv: Double,
    @SerializedName("air_quality") val airQuality: AirQualityResponse
)

data class ConditionResponse(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("code") val code: Int
)

data class AirQualityResponse(
    @SerializedName("co") val co: String,
    @SerializedName("no2") val no2: String,
    @SerializedName("o3") val o3: String,
    @SerializedName("so2") val so2: String,
    @SerializedName("pm2_5") val pm25: String,
    @SerializedName("pm10") val pm10: String,
    @SerializedName("us-epa-index") val usEpaIndex: Int,
    @SerializedName("gb-defra-index") val gbDefraIndex: Int
)

data class AstroResponse(
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("sunset") val sunset: String,
    @SerializedName("moonrise") val moonrise: String,
    @SerializedName("moonset") val moonset: String,
    @SerializedName("moon_phase") val moonPhase: String,
    @SerializedName("moon_illumination") val moonIllumination: String
)

data class HourResponse(
    @SerializedName("time_epoch") val timeEpoch: Int,
    @SerializedName("time") val time: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("condition") val condition: ConditionResponse,
    @SerializedName("wind_mph") val windMph: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDir: String,
    @SerializedName("pressure_mb") val pressureMb: Double,
    @SerializedName("pressure_in") val pressureIn: Double,
    @SerializedName("precip_mm") val precipMm: Double,
    @SerializedName("precip_in") val precipIn: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("feelslike_c") val feelsLikeC: Double,
    @SerializedName("feelslike_f") val feelsLikeF: Double,
    @SerializedName("windchill_c") val windchillC: Double,
    @SerializedName("windchill_f") val windchillF: Double,
    @SerializedName("heatindex_c") val heatIndexC: Double,
    @SerializedName("heatindex_f") val heatIndexF: Double,
    @SerializedName("dewpoint_c") val dewpointC: Double,
    @SerializedName("dewpoint_f") val dewpointF: Double,
    @SerializedName("will_it_rain") val willItRain: Int,
    @SerializedName("chance_of_rain") val chanceOfRain: Int,
    @SerializedName("will_it_snow") val willItSnow: Int,
    @SerializedName("chance_of_snow") val chanceOfSnow: Int,
    @SerializedName("vis_km") val visKm: Double,
    @SerializedName("vis_miles") val visMiles: Double,
    @SerializedName("gust_mph") val gustMph: Double,
    @SerializedName("gust_kph") val gustKph: Double,
    @SerializedName("uv") val uv: Double,
    @SerializedName("air_quality") val airQuality: AirQualityResponse
)