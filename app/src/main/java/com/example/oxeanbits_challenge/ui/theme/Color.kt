package com.example.oxeanbits_challenge.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.oxeanbits_challenge.weather.model.WeatherCondition

val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val ColorSnowDay = Color(0xFFFFFFFF)
val ColorRainyDay = Color(0xFF555555)
val ColorClearNight = Color(0xFF1E1E3F)
val ColorCloudy = Color(0xFF808080)
val ColorClearDay = Color(0xFF87CEEB)
val ColorFog = Color(0xFFA9A9A9 )
val ColorRime = Color(0xFFB0E0E6)
val ColorDrizzle = Color(0xFF708090)
val ColorFreezingDrizzle = Color(0xFF7B68EE)
val ColorThunderstorm = Color(0xFFFFA500)

val BackgroundColor: (WeatherCondition) -> Color = { weather ->
    when(weather) {
        WeatherCondition.CLEAR_SKY_DAY -> ColorClearDay
        WeatherCondition.CLEAR_SKY_NIGHT -> ColorClearNight
        WeatherCondition.CLOUDY -> ColorCloudy
        WeatherCondition.FOG -> ColorFog
        WeatherCondition.RIME -> ColorRime
        WeatherCondition.DRIZZLE -> ColorDrizzle
        WeatherCondition.FREEZING_DRIZZLE -> ColorFreezingDrizzle
        WeatherCondition.RAIN -> ColorRainyDay
        WeatherCondition.FREEZING_RAIN -> ColorRainyDay
        WeatherCondition.SNOW -> ColorSnowDay
        WeatherCondition.THUNDER_STORM -> ColorThunderstorm
    }
}

val ColorWord: (WeatherCondition) -> Color = { weather ->
    when(weather) {
        WeatherCondition.CLEAR_SKY_DAY -> Color.Black
        WeatherCondition.CLEAR_SKY_NIGHT -> Color.White
        WeatherCondition.FOG -> Color.Black
        WeatherCondition.RIME -> Color.Black
        WeatherCondition.DRIZZLE -> Color.White
        WeatherCondition.FREEZING_DRIZZLE -> Color.Black
        WeatherCondition.FREEZING_RAIN -> Color.White
        WeatherCondition.THUNDER_STORM -> Color.Black
        WeatherCondition.SNOW -> Color.Black
        WeatherCondition.RAIN -> Color.White
        WeatherCondition.CLOUDY -> Color.White
    }
}