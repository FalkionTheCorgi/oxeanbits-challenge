package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.oxeanbits_challenge.database.entity.CurrentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentDao {
    @Insert
    suspend fun insert(current: CurrentEntity)

    @Delete
    suspend fun delete(current: CurrentEntity)

    @Update
    suspend fun update(current: CurrentEntity)

    @Query("SELECT * FROM current_entity")
    fun getAllCurrent(): Flow<List<CurrentEntity>>
}
