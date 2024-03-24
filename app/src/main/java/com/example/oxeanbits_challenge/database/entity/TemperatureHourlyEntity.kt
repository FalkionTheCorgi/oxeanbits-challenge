package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "temperature2m_hourly_entity", foreignKeys = [
    ForeignKey(
        entity = HourlyEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class TemperatureHourlyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val temperature2m: Double,
    val hourlyId: Long
)