package com.example.oxeanbits_challenge.database.dao

import androidx.room.*
import com.example.oxeanbits_challenge.database.ForecastResponseWithDetails
import com.example.oxeanbits_challenge.database.entity.ForecastResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastResponse: ForecastResponseEntity)

    @Delete
    suspend fun delete(forecastResponse: ForecastResponseEntity)

    @Update
    suspend fun update(forecastResponse: ForecastResponseEntity)

    @Transaction
    @Query("SELECT * FROM forecast_response")
    fun getForecastWithRelations(): Flow<ForecastResponseWithDetails>

    @Transaction
    @Query("SELECT * FROM forecast_response WHERE date = :date AND location = :location")
    fun getForecastWithRelationsByDate(date: String, location: String): Flow<ForecastResponseWithDetails>
}