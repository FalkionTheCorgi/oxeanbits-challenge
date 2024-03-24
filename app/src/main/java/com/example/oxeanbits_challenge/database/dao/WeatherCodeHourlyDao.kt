package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.oxeanbits_challenge.database.entity.WeatherCodeHourlyEntity

@Dao
interface WeatherCodeHourlyDao {
    @Insert
    suspend fun insertWeatherCode(weatherCode: WeatherCodeHourlyEntity)

}