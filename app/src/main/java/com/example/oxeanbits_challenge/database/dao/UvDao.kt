package com.example.oxeanbits_challenge.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.oxeanbits_challenge.database.UVWithDetails
import com.example.oxeanbits_challenge.database.entity.UVEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UvDao {
    @Insert
    suspend fun insert(uv: UVEntity)

    @Delete
    suspend fun delete(uv: UVEntity)

    @Update
    suspend fun update(uv: UVEntity)

    @Transaction
    @Query("SELECT * FROM uv_entity")
    fun getUvWithRelations(): Flow<UVWithDetails>

}
