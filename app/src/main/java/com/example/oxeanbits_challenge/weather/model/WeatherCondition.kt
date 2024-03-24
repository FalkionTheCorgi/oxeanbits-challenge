package com.example.oxeanbits_challenge.weather.model

import com.example.oxeanbits_challenge.R

enum class WeatherCondition {
    CLEAR_SKY_DAY,
    CLEAR_SKY_NIGHT,
    CLOUDY,
    FOG,
    RIME,
    DRIZZLE,
    FREEZING_DRIZZLE,
    RAIN,
    FREEZING_RAIN,
    SNOW,
    THUNDER_STORM;



    fun getArchive(): Int {
        return when(this){
            THUNDER_STORM -> { R.raw.thunderstorms }
            DRIZZLE -> { R.raw.drizzle }
            RAIN -> { R.raw.rain }
            SNOW -> { R.raw.snow }
            RIME -> { R.raw.rime }
            FOG -> { R.raw.fog }
            CLOUDY -> { R.raw.cloudy }
            CLEAR_SKY_DAY -> { R.raw.clear_day }
            CLEAR_SKY_NIGHT -> { R.raw.clear_night }
            FREEZING_DRIZZLE -> { R.raw.rain }
            FREEZING_RAIN -> { R.raw.rain }
        }
    }
}

fun mapIntWithWeather(state: Int, isDay: Boolean?): WeatherCondition {
    return when(state){
        0 -> {
            if (isDay != null && isDay) WeatherCondition.CLEAR_SKY_DAY else WeatherCondition.CLEAR_SKY_NIGHT
        }
        1,2,3 -> {
            WeatherCondition.CLOUDY
        }
        45,48 -> {
            WeatherCondition.FOG
        }
        51,53,55,56,57 -> {
            WeatherCondition.DRIZZLE
        }
        61,63,65,66,67,80,81,82 ->{
            WeatherCondition.RAIN
        }
        71,73,75,77,85,86 -> {
            WeatherCondition.SNOW
        }
        95,96,99 -> {
            WeatherCondition.THUNDER_STORM
        }
        else -> WeatherCondition.CLEAR_SKY_DAY
    }
}

/*0	Clear sky
1, 2, 3	Mainly clear, partly cloudy, and overcast
45, 48	Fog and depositing rime fog
51, 53, 55	Drizzle: Light, moderate, and dense intensity
56, 57	Freezing Drizzle: Light and dense intensity
61, 63, 65	Rain: Slight, moderate and heavy intensity
66, 67	Freezing Rain: Light and heavy intensity
71, 73, 75	Snow fall: Slight, moderate, and heavy intensity
77	Snow grains
80, 81, 82	Rain showers: Slight, moderate, and violent
85, 86	Snow showers slight and heavy
95 *	Thunderstorm: Slight or moderate
96, 99 *	Thunderstorm with slight and heavy hail*/