package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_entity")
data class HourlyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)