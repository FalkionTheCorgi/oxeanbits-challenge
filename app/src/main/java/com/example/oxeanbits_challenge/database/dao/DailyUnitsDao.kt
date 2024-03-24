package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.DailyUnitsWithDetails
import com.example.oxeanbits_challenge.database.entity.DailyUnitsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyUnitsDao {
    @Insert
    suspend fun insert(dailyUnits: DailyUnitsEntity)

    @Delete
    suspend fun delete(dailyUnits: DailyUnitsEntity)

    @Update
    suspend fun update(dailyUnits: DailyUnitsEntity)

    @Query("SELECT * FROM daily_units_entity")
    fun getAllDailyUnits(): Flow<List<DailyUnitsEntity>>

    @Transaction
    @Query("SELECT * FROM daily_units_entity")
    fun getDailyUnitsHourlyWithRelations(): Flow<DailyUnitsWithDetails>

}