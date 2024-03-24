package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.RelativeHumidityHourlyWithDetails
import com.example.oxeanbits_challenge.database.entity.RelativeHumidityHourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RelativeHumidityHourlyDao {
    @Insert
    suspend fun insert(relativeHumidity: RelativeHumidityHourlyEntity)

    @Delete
    suspend fun delete(relativeHumidity: RelativeHumidityHourlyEntity)

    @Update
    suspend fun update(relativeHumidity: RelativeHumidityHourlyEntity)

    @Transaction
    @Query("SELECT * FROM relative_humidity2m_hourly_entity")
    fun getRelativeHumidityHourlyWithRelations(): Flow<List<RelativeHumidityHourlyWithDetails>>

}
