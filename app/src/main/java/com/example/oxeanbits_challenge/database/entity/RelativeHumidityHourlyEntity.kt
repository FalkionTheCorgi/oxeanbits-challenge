package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "relative_humidity2m_hourly_entity", foreignKeys = [
    ForeignKey(
        entity = HourlyEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class RelativeHumidityHourlyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val relativeHumidity2m: Int,
    val hourlyId: Long
)