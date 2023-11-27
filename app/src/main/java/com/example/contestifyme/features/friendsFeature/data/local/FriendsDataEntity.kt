package com.example.contestifyme.features.friendsFeature.data.local

import androidx.room.Entity

@Entity(tableName = "friends_data", primaryKeys = ["id", "handle"])
data class FriendsDataEntity (
    val id: Int,
    val handle: String,
    val avatar: String?,
    val contribution: Int?,
    val country: String?,
    val name: String?,
    val friendOfCount: Int?,
    val lastOnlineTimeSeconds: Int?,
    val maxRank: String?,
    val maxRating: Int?,
    val organization: String?,
    val rank: String?,
    val rating: Int?,
    val registrationTimeSeconds: Int?,
    val titlePhoto: String?,
)