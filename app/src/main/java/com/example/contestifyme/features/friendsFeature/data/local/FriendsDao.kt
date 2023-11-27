package com.example.contestifyme.features.friendsFeature.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface FriendsDao {
    @Upsert
    suspend fun upsert(friends: List<FriendsDataEntity>)
    @Query("SELECT * FROM friends_data")
    fun getAllFriends(): Flow<List<FriendsDataEntity>>
}