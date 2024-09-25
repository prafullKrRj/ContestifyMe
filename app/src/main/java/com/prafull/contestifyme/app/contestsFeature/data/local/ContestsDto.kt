package com.prafull.contestifyme.app.contestsFeature.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contests")
data class ContestsEntity(
    @PrimaryKey
    val id: Int,
    val durationSeconds: Int,
    val frozen: Boolean,
    val name: String,
    val phase: String,
    val relativeTimeSeconds: Int,
    val startTimeSeconds: Int,
    val type: String
)