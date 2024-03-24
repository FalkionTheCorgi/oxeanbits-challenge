package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.oxeanbits_challenge.database.entity.CurrentUnitsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentUnitsDao {
    @Insert
    suspend fun insert(currentUnits: CurrentUnitsEntity)

    @Delete
    suspend fun delete(currentUnits: CurrentUnitsEntity)

    @Update
    suspend fun update(currentUnits: CurrentUnitsEntity)

    @Query("SELECT * FROM current_units_entity")
    fun getAllCurrentUnits(): Flow<List<CurrentUnitsEntity>>
}
