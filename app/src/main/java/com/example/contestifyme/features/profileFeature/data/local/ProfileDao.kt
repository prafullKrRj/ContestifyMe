package com.example.contestifyme.features.profileFeature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserRating
import com.example.contestifyme.features.profileFeature.data.local.entities.UserStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userInfoEntity: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    fun getUserInfo(): Flow<List<UserInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRating(userRating: List<UserRating>)

    @Query("SELECT * FROM user_rating")
    fun getUserRating(): Flow<List<UserRating>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserStatus(userStatus: List<UserStatus>)

    @Query("SELECT * FROM user_status")
    fun getUserStatus(): Flow<List<UserStatus>>
}