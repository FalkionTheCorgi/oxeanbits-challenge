package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "sunset_entity", foreignKeys = [
    ForeignKey(
        entity = DailyEntity::class,
        parentColumns = ["id"],
        childColumns = ["dailyEntityId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class SunsetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sunset: String,
    val dailyEntityId: Long
)