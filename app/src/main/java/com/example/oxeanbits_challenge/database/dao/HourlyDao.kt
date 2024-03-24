package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.oxeanbits_challenge.database.entity.HourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyDao {
    @Insert
    suspend fun insert(hourly: HourlyEntity)

    @Delete
    suspend fun delete(hourly: HourlyEntity)

    @Update
    suspend fun update(hourly: HourlyEntity)

    @Query("SELECT * FROM hourly_entity")
    fun getAllHourly(): Flow<List<HourlyEntity>>

}