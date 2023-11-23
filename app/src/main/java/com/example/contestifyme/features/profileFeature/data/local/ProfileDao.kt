package com.example.contestifyme.features.profileFeature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userInfoEntity: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    fun getUserInfo(): Flow<List<UserInfoEntity>>

    @Update
    suspend fun updateUserInfo(userInfoEntity: UserInfoEntity)
}