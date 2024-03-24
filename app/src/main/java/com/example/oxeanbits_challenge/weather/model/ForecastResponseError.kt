package com.example.oxeanbits_challenge.weather.model

import com.example.oxeanbits_challenge.api.model.response.Current
import com.example.oxeanbits_challenge.api.model.response.CurrentUnits
import com.example.oxeanbits_challenge.api.model.response.Daily
import com.example.oxeanbits_challenge.api.model.response.DailyUnits
import com.example.oxeanbits_challenge.api.model.response.ForecastResponse
import com.example.oxeanbits_challenge.api.model.response.Hourly
import com.example.oxeanbits_challenge.api.model.response.HourlyUnits

val forecastResponseError = ForecastResponse(
    latitude = 0.0,
    longitude = 0.0,
    generationtimeMs = 0.0,
    utcOffsetSeconds = 0,
    timezone = "UNDEFINED",
    timezoneAbbreviation = "UNDEFINED",
    elevation = 0,
    currentUnits = CurrentUnits(
        time = "",
        interval = "",
        temperature2m = "",
        relativeHumidity2m = "",
        apparentTemperature = "",
        weatherCode = "",
        isDay = ""
    ),
    current = Current(
        time = "",
        interval = 0,
        temperature2m = 0.0,
        relativeHumidity2m = 0,
        apparentTemperature = 0.0,
        weatherCode = 0,
        isDay = 0
    ),
    hourlyUnits = HourlyUnits(
        time = "",
        temperature2m = "",
        relativeHumidity2m = "",
        windSpeed10m = "",
        weatherCode = "",
        precipitation = ""
    ),
    hourly = Hourly(
        time = arrayListOf(),
        temperature2m = arrayListOf(),
        relativeHumidity2m = arrayListOf(),
        windSpeed10m = arrayListOf(),
        weatherCode = arrayListOf(),
        precipitation = arrayListOf()
    ),
    dailyUnits = DailyUnits(
        time = "",
        uvIndexMax = "",
        weatherCode = "",
        temperature2mMax = "",
        temperature2mMin = "",
        precipitationProbabilityMax = ""
    ),
    daily = Daily(
        time = arrayListOf(),
        uvIndexMax = arrayListOf(),
        sunrise = arrayListOf(),
        sunset = arrayListOf(),
        weatherCode = arrayListOf(),
        temperature2mMax = arrayListOf(),
        temperature2mMin = arrayListOf(),
        precipitationProbabilityMax = arrayListOf()
    )
)