package com.example.oxeanbits_challenge.api.model.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse (

    @SerializedName("latitude"              ) var latitude             : Double?       = null,
    @SerializedName("longitude"             ) var longitude            : Double?       = null,
    @SerializedName("generationtime_ms"     ) var generationtimeMs     : Double?       = null,
    @SerializedName("utc_offset_seconds"    ) var utcOffsetSeconds     : Int?          = null,
    @SerializedName("timezone"              ) var timezone             : String?       = null,
    @SerializedName("timezone_abbreviation" ) var timezoneAbbreviation : String?       = null,
    @SerializedName("elevation"             ) var elevation            : Int?          = null,
    @SerializedName("current_units"         ) var currentUnits         : CurrentUnits? = CurrentUnits(),
    @SerializedName("current"               ) var current              : Current?      = Current(),
    @SerializedName("hourly_units"          ) var hourlyUnits          : HourlyUnits?  = HourlyUnits(),
    @SerializedName("hourly"                ) var hourly               : Hourly?       = Hourly(),
    @SerializedName("daily_units"           ) var dailyUnits           : DailyUnits?   = DailyUnits(),
    @SerializedName("daily"                 ) var daily                : Daily?        = Daily()

)

data class CurrentUnits (

    @SerializedName("time"                 ) var time                : String? = null,
    @SerializedName("interval"             ) var interval            : String? = null,
    @SerializedName("temperature_2m"       ) var temperature2m       : String? = null,
    @SerializedName("relative_humidity_2m" ) var relativeHumidity2m  : String? = null,
    @SerializedName("apparent_temperature" ) var apparentTemperature : String? = null,
    @SerializedName("weather_code"         ) var weatherCode         : String? = null,
    @SerializedName("is_day"               ) var isDay               : String? = null

)



data class Current (

    @SerializedName("time"                 ) var time                : String? = null,
    @SerializedName("interval"             ) var interval            : Int?    = null,
    @SerializedName("temperature_2m"       ) var temperature2m       : Double? = null,
    @SerializedName("relative_humidity_2m" ) var relativeHumidity2m  : Int?    = null,
    @SerializedName("apparent_temperature" ) var apparentTemperature : Double? = null,
    @SerializedName("weather_code"         ) var weatherCode         : Int? = null,
    @SerializedName("is_day"               ) var isDay               : Int?    = null

)


data class HourlyUnits (

    @SerializedName("time"                            ) var time               : String? = null,
    @SerializedName("temperature_2m"                  ) var temperature2m      : String? = null,
    @SerializedName("relative_humidity_2m"            ) var relativeHumidity2m : String? = null,
    @SerializedName("wind_speed_10m"                  ) var windSpeed10m       : String? = null,
    @SerializedName("weather_code"                    ) var weatherCode        : String? = null,
    @SerializedName("precipitation_probability"       ) var precipitation      : String? = null,

)

data class Hourly (

    @SerializedName("time"                          ) var time               : ArrayList<String> = arrayListOf(),
    @SerializedName("temperature_2m"                ) var temperature2m      : ArrayList<Double> = arrayListOf(),
    @SerializedName("relative_humidity_2m"          ) var relativeHumidity2m : ArrayList<Int>    = arrayListOf(),
    @SerializedName("wind_speed_10m"                ) var windSpeed10m       : ArrayList<Double> = arrayListOf(),
    @SerializedName("weather_code"                  ) var weatherCode        : ArrayList<Int>    = arrayListOf(),
    @SerializedName("precipitation_probability"     ) var precipitation      : ArrayList<Int>    = arrayListOf(),

)

data class Daily (

    @SerializedName("time"                          ) var time                         : ArrayList<String> = arrayListOf(),
    @SerializedName("uv_index_max"                  ) var uvIndexMax                   : ArrayList<Double> = arrayListOf(),
    @SerializedName("sunrise"                       ) var sunrise                      : ArrayList<String> = arrayListOf(),
    @SerializedName("sunset"                        ) var sunset                       : ArrayList<String> = arrayListOf(),
    @SerializedName("weather_code"                  ) var weatherCode                  : ArrayList<Int>    = arrayListOf(),
    @SerializedName("temperature_2m_max"            ) var temperature2mMax             : ArrayList<Double> = arrayListOf(),
    @SerializedName("temperature_2m_min"            ) var temperature2mMin             : ArrayList<Double> = arrayListOf(),
    @SerializedName("precipitation_probability_max" ) var precipitationProbabilityMax  : ArrayList<Int>    = arrayListOf(),


)


data class DailyUnits (

    @SerializedName("time"                          ) var time                         : String? = null,
    @SerializedName("uv_index_max"                  ) var uvIndexMax                   : String? = null,
    @SerializedName("weather_code"                  ) var weatherCode                  : String? = null,
    @SerializedName("temperature_2m_max"            ) var temperature2mMax             : String? = null,
    @SerializedName("temperature_2m_min"            ) var temperature2mMin             : String? = null,
    @SerializedName("precipitation_probability_max" ) var precipitationProbabilityMax  : String? = null,


)