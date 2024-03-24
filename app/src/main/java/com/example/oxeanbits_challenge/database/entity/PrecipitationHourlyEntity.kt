package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "precipitation_hourly_entity", foreignKeys = [
    ForeignKey(
        entity = HourlyEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class PrecipitationHourlyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val precipitation: Int,
    val hourlyId: Long
)