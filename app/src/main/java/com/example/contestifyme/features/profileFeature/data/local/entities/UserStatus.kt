package com.example.contestifyme.features.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_status")
data class UserStatus(
    @PrimaryKey
    val id: Int,
    val name: String,
    val verdict: String,
    val time: Int,
    val contestId: Int,
)
