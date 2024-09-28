package com.prafull.contestifyme.app.profileFeature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userInfoEntity: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    fun getUserInfo(): Flow<List<UserInfoEntity>>

    @Update
    suspend fun updateUserInfo(userInfoEntity: UserInfoEntity)

    @Query("SELECT * FROM user_info WHERE handle = :userHandle")
    suspend fun getUser(userHandle: String): UserInfoEntity
}