package com.prafull.contestifyme.app.friendsFeature.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity


@Dao
interface FriendsDao {

    @Upsert
    suspend fun insertUserInfo(userInfoEntity: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersInfo(infos: List<UserInfoEntity>)

    @Query("SELECT * FROM user_info WHERE handle = :handle")
    suspend fun getUserInfo(handle: String): UserInfoEntity?

    @Query("select * from user_info order by rating DESC")
    suspend fun getFriendsData(): List<UserInfoEntity>

    @Delete
    suspend fun deleteFriend(userInfoEntity: UserInfoEntity)

    @Query("delete from user_info")
    suspend fun deleteAll()

    @Query("DELETE FROM user_info WHERE handle IN (:map)")
    suspend fun deleteFriends(map: List<String>)

}