package com.example.contestifyme.features.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_status")
data class UserStatusEntity(
    @PrimaryKey
    val id: Int,
    val name: Int,
    val verdict: String,
    val time: Int,
    val contestId: Int,
)
