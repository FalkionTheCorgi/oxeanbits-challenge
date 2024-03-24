package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.HourlyUnitsWithDetails
import com.example.oxeanbits_challenge.database.entity.HourlyUnitsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyUnitsDao {
    @Insert
    suspend fun insert(hourlyUnits: HourlyUnitsEntity)

    @Delete
    suspend fun delete(hourlyUnits: HourlyUnitsEntity)

    @Update
    suspend fun update(hourlyUnits: HourlyUnitsEntity)

    @Query("SELECT * FROM hourly_units_entity")
    fun getAllHourlyUnits(): Flow<List<HourlyUnitsEntity>>

    @Transaction
    @Query("SELECT * FROM hourly_units_entity")
    fun getAllHourlyUnitsWithDesc(): Flow<HourlyUnitsWithDetails>

}