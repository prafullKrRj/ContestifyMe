package com.example.contestifyme.features.profileFeature.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserStatus(
    val id: Int,
    val name: String,
    val verdict: String,
    val time: Int,
    val contestId: Int,
)
