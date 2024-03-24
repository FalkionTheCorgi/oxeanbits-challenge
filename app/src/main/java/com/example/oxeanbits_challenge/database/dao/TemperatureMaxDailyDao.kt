package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.oxeanbits_challenge.database.entity.TemperatureMaxDailyEntity

@Dao
interface TemperatureMaxDailyDao {
    @Insert
    suspend fun insertTemperatureMax(temperature: TemperatureMaxDailyEntity)

}