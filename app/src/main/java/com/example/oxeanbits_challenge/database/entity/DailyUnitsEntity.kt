package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "daily_units_entity", foreignKeys = [
    ForeignKey(
        entity = DailyEntity::class,
        parentColumns = ["id"],
        childColumns = ["dailyEntityId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class DailyUnitsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String?,
    val uvIndexMax: String?,
    val weatherCode: String?,
    val temperatureMax: String?,
    val temperatureMin: String?,
    val dailyEntityId: Long
)