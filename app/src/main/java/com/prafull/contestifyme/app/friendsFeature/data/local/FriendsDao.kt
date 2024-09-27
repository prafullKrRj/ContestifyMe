package com.prafull.contestifyme.app.friendsFeature.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prafull.contestifyme.onboard.model.UserResult


@Dao
interface FriendsDao {

    @Delete
    suspend fun deleteFriend(friendEntity: FriendEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friendEntity: FriendEntity)

    @Query("SELECT friendInfo from FriendEntity order by rating DESC")
    suspend fun getFriendsList(): List<UserResult>

    @Query("SELECT * from FriendEntity where handle = :requiredHandle")
    suspend fun getFriendData(requiredHandle: String): FriendEntity
}