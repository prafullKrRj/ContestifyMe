package com.example.contestifyme.features.profileFeature.data

import com.example.contestifyme.features.profileFeature.constants.ProfileConstants
import com.example.contestifyme.features.profileFeature.data.local.ProfileDao
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserRatingEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserStatusEntity
import com.example.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.example.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProfileRepository {


    suspend fun insertUser(userInfoEntity: UserInfoEntity)
    fun getUserInfo(): Flow<List<UserInfoEntity>>
    /**
     *  This function is used to get the user info from the API
     *  */
    suspend fun getUserInfoFromApi(handle: String): ProfileUserDto
}

class ProfileRepositoryImpl (
    private val profileApiService: ProfileApiService,
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun insertUser(userInfoEntity: UserInfoEntity) {
        profileDao.insertUser(userInfoEntity)
    }

    override fun getUserInfo(): Flow<List<UserInfoEntity>> {
        return profileDao.getUserInfo()
    }

    override suspend fun getUserInfoFromApi(handle: String): ProfileUserDto =
        profileApiService.getUserInfoFromApi(ProfileConstants.getUserInfo(handle))

}