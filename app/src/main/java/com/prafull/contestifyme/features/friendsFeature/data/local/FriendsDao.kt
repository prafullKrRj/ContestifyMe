package com.prafull.contestifyme.features.friendsFeature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FriendsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriendsData(data: List<FriendsDataEntity>)

    @Query("SELECT * FROM friends order by rating desc")
    fun getFriendsData(): Flow<List<FriendsDataEntity>>
}