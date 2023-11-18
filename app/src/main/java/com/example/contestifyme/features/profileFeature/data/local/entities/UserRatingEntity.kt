package com.example.contestifyme.features.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_rating")
data class UserRatingEntity(
    @PrimaryKey
    val contestId: Int,
    val contestName: String,
    val handle: String,
    val newRating: Int,
    val oldRating: Int,
    val rank: Int,
    val ratingUpdateTimeSeconds: Int
)
