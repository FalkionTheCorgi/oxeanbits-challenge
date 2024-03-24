package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_units_entity")
data class CurrentUnitsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String?,
    val interval: String?,
    val temperature2m: String?,
    val relativeHumidity2m: String?,
    val apparentTemperature: String?,
    val weatherCode: String?,
    val isDay: String?
)