package com.example.oxeanbits_challenge.weather.model

data class ForecastData(
    val temperature: String,
    val maxTemperature: String,
    val minTemperature: String,
    val relativeHumidity: String,
    val probabilityPrecipitation: String,
    val ultravioletIndex: Double,
    val dayOrNight: String?,
    val sunrise: String,
    val sunset: String,
    val weatherCode: Int,
    val timezone: String
)

val forecastDataError = ForecastData(
    temperature = "UNDEFINED",
    maxTemperature = "UNDEFINED",
    minTemperature = "UNDEFINED",
    relativeHumidity = "UNDEFINED",
    probabilityPrecipitation = "UNDEFINED",
    ultravioletIndex = 0.00,
    dayOrNight = null,
    sunrise = "UNDEFINED",
    sunset = "UNDEFINED",
    weatherCode = -1,
    timezone = "UNDEFINED"
)
