package com.example.contestifyme.features.profileFeature.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserRatingEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {


    @Upsert(entity = UserInfoEntity::class)
    suspend fun upsertProfile(userInfoEntity: UserInfoEntity)

    @Upsert(entity = UserRatingEntity::class)
    suspend fun upsertUserRating(insertAll: List<UserRatingEntity>)

    @Upsert(entity = UserStatusEntity::class)
    suspend fun upsertSubmissions(insertAll: List<UserStatusEntity>)

    @Query("SELECT * FROM user_info WHERE id = :id")
    fun getProfile(id: Int): Flow<List<UserInfoEntity>>

    @Query("SELECT * FROM user_rating")
    fun getUserRating(): Flow<List<UserRatingEntity>>

    @Query("SELECT * FROM user_status")
    fun getUserSubmissions(): Flow<List<UserStatusEntity>>

}