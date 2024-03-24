package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "uv_entity", foreignKeys = [
    ForeignKey(
        entity = DailyEntity::class,
        parentColumns = ["id"],
        childColumns = ["dailyEntityId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class UVEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uv: Double,
    val dailyEntityId: Long
)