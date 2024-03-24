package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.SunsetWithDetails
import com.example.oxeanbits_challenge.database.entity.SunsetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SunsetDao {
    @Insert
    suspend fun insert(sunset: SunsetEntity)

    @Delete
    suspend fun delete(sunset: SunsetEntity)

    @Update
    suspend fun update(sunset: SunsetEntity)

    @Transaction
    @Query("SELECT * FROM sunset_entity")
    fun getSunsetWithRelations(): Flow<SunsetWithDetails>
}
