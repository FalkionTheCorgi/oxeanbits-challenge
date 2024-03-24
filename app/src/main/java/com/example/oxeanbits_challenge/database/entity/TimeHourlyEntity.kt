package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "time_hourly_entity", foreignKeys = [
    ForeignKey(
        entity = HourlyEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class TimeHourlyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String,
    val hourlyId: Long
)