package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.WindSpeedHourlyWithDetails
import com.example.oxeanbits_challenge.database.entity.WindSpeedHourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WindSpeedHourlyDao {
    @Insert
    suspend fun insert(windSpeed: WindSpeedHourlyEntity)

    @Delete
    suspend fun delete(windSpeed: WindSpeedHourlyEntity)

    @Update
    suspend fun update(windSpeed: WindSpeedHourlyEntity)

    @Transaction
    @Query("SELECT * FROM windSpeed10m_hourly_entity")
    fun getWindSpeedHourlyWithRelations(): Flow<WindSpeedHourlyWithDetails>

}