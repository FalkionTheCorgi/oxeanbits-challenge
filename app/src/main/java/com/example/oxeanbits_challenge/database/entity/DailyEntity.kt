package com.example.oxeanbits_challenge.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_entity")
data class DailyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)