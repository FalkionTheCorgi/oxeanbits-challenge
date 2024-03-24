package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_units_entity", foreignKeys = [
    ForeignKey(
        entity = HourlyEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class HourlyUnitsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String?,
    val temperature2m: String?,
    val relativeHumidity2m: String?,
    val windSpeed10m: String?,
    val precipitation: String?,
    val weatherCode: String?,
    val hourlyId: Long
)