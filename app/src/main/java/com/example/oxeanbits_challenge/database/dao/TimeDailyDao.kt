package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.TimeDailyWithDetails
import com.example.oxeanbits_challenge.database.entity.TimeDailyEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TimeDailyDao {
    @Insert
    suspend fun insert(timeDaily: TimeDailyEntity)

    @Delete
    suspend fun delete(timeDaily: TimeDailyEntity)

    @Update
    suspend fun update(timeDaily: TimeDailyEntity)

    @Transaction
    @Query("SELECT * FROM time_daily_entity")
    fun getTimeDailyWithRelations(): Flow<TimeDailyWithDetails>

}