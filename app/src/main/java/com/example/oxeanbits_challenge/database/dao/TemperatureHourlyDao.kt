package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.TemperatureHourlyWithDetails
import com.example.oxeanbits_challenge.database.entity.TemperatureHourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemperatureHourlyDao {
    @Insert
    suspend fun insert(temperature: TemperatureHourlyEntity)

    @Delete
    suspend fun delete(temperature: TemperatureHourlyEntity)

    @Update
    suspend fun update(temperature: TemperatureHourlyEntity)

    @Transaction
    @Query("SELECT * FROM temperature2m_hourly_entity")
    fun getTemperatureHourlyWithRelations(): Flow<List<TemperatureHourlyWithDetails>>

}