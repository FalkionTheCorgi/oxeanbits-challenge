package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.PrecipitationHourlyWithDetails
import com.example.oxeanbits_challenge.database.entity.PrecipitationHourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrecipitationHourlyDao {
    @Insert
    suspend fun insert(precipitation: PrecipitationHourlyEntity)

    @Delete
    suspend fun delete(precipitation: PrecipitationHourlyEntity)

    @Update
    suspend fun update(precipitation: PrecipitationHourlyEntity)

    @Transaction
    @Query("SELECT * FROM precipitation_hourly_entity")
    fun getPrecipitationHourlyWithRelations(): Flow<List<PrecipitationHourlyWithDetails>>

}