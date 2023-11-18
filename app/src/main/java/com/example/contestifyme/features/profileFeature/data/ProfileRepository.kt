package com.example.contestifyme.features.profileFeature.data

import android.util.Log
import com.example.contestifyme.features.profileFeature.constants.Constants
import com.example.contestifyme.features.profileFeature.data.local.ProfileDao
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserRatingEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserStatusEntity
import com.example.contestifyme.features.profileFeature.data.remote.ProfileApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface ProfileRepository {

    suspend fun upsertProfile(userInfoEntity: UserInfoEntity)
    suspend fun upsertUserRating(insertAll: List<UserRatingEntity>)
    suspend fun upsertSubmissions(insertAll: List<UserStatusEntity>)

    fun getProfile(id: Int, handle: String): Flow<List<UserInfoEntity>>
    fun getUserRating(): Flow<List<UserRatingEntity>>
    fun getUserSubmissions(): Flow<List<UserStatusEntity>>
}

class ProfileRepositoryImpl (
    private val profileApiService: ProfileApiService,
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun upsertProfile(userInfoEntity: UserInfoEntity) = profileDao.upsertProfile(userInfoEntity)

    override suspend fun upsertUserRating(insertAll: List<UserRatingEntity>) = profileDao.upsertUserRating(insertAll)

    override suspend fun upsertSubmissions(insertAll: List<UserStatusEntity>) = profileDao.upsertSubmissions(insertAll)

    override fun getProfile(id: Int, handle: String): Flow<List<UserInfoEntity>> = flow {

    }
    override fun getUserRating(): Flow<List<UserRatingEntity>> {
        TODO("Not yet implemented")
    }

    override fun getUserSubmissions(): Flow<List<UserStatusEntity>> {
        TODO("Not yet implemented")
    }

}