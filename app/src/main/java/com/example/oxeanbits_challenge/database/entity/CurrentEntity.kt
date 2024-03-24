package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_entity")
data class CurrentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String?,
    val interval: Int?,
    val temperature2m: Double?,
    val relativeHumidity2m: Int?,
    val apparentTemperature: Double?,
    val weatherCode: Int?,
    val isDay: Int?
)