package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.TimeHourlyWithDetails
import com.example.oxeanbits_challenge.database.entity.TimeHourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeHourlyDao {
    @Insert
    suspend fun insert(time: TimeHourlyEntity)

    @Delete
    suspend fun delete(time: TimeHourlyEntity)

    @Update
    suspend fun update(time: TimeHourlyEntity)

    @Transaction
    @Query("SELECT * FROM time_hourly_entity")
    fun getTimeHourlyWithRelations(): Flow<List<TimeHourlyWithDetails>>

}