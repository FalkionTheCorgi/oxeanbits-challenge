package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.oxeanbits_challenge.database.entity.DailyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDao {
    @Insert
    suspend fun insert(daily: DailyEntity)

    @Delete
    suspend fun delete(daily: DailyEntity)

    @Update
    suspend fun update(daily: DailyEntity)

    @Query("SELECT * FROM daily_entity")
    fun getAllDaily(): Flow<List<DailyEntity>>
}
