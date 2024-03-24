package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.SunriseWithDetails
import com.example.oxeanbits_challenge.database.entity.SunriseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SunriseDao {
    @Insert
    suspend fun insert(sunrise: SunriseEntity)

    @Delete
    suspend fun delete(sunrise: SunriseEntity)

    @Update
    suspend fun update(sunrise: SunriseEntity)

    @Transaction
    @Query("SELECT * FROM sunrise_entity")
    fun getSunriseWithRelations(): Flow<SunriseWithDetails>

}