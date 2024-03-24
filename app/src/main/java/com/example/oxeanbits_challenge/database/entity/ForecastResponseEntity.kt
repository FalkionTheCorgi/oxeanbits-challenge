package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_response", foreignKeys = [
    ForeignKey(
        entity = CurrentUnitsEntity::class,
        parentColumns = ["id"],
        childColumns = ["currentUnitsId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = CurrentEntity::class,
        parentColumns = ["id"],
        childColumns = ["currentId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = HourlyUnitsEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyUnitsId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = HourlyEntity::class,
        parentColumns = ["id"],
        childColumns = ["hourlyId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = DailyUnitsEntity::class,
        parentColumns = ["id"],
        childColumns = ["dailyUnitsId"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = DailyEntity::class,
        parentColumns = ["id"],
        childColumns = ["dailyId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class ForecastResponseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val latitude: Double?,
    val longitude: Double?,
    val location: String?,
    val generationtimeMs: Double?,
    val utcOffsetSeconds: Int?,
    val timezone: String?,
    val timezoneAbbreviation: String?,
    val elevation: Int?,
    val currentUnitsId: Long,
    val currentId: Long,
    val hourlyUnitsId: Long,
    val hourlyId: Long,
    val dailyUnitsId: Long,
    val dailyId: Long
)

