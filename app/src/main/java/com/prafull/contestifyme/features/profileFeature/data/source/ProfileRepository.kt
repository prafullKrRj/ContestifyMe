package com.prafull.contestifyme.features.profileFeature.data.source

import com.prafull.contestifyme.features.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     *  User Info
     * */
    suspend fun insertUser(userInfoEntity: UserInfoEntity)
    fun getUserInfo(): Flow<List<UserInfoEntity>>
    suspend fun getUserInfoFromApi(handle: String): ProfileUserDto
    suspend fun getUserRatingFromApi(handle: String): RatingDto
    suspend fun updateUserInfo(userInfoEntity: UserInfoEntity)
    suspend fun getUserStatusFromApi(handle: String): SubmissionDto
}

class ProfileRepositoryImpl (
    private val profileApiService: ProfileApiService,
    private val profileDao: ProfileDao
) : ProfileRepository {


    /**
     *  User Info
     * */
    override suspend fun insertUser(userInfoEntity: UserInfoEntity) {
        profileDao.insertUser(userInfoEntity)
    }

    override fun getUserInfo(): Flow<List<UserInfoEntity>> {
        return profileDao.getUserInfo()
    }

    override suspend fun getUserInfoFromApi(handle: String): ProfileUserDto =
        profileApiService.getUserInfoFromApi(ProfileConstants.getUserInfo(handle))


    override suspend fun getUserRatingFromApi(handle: String): RatingDto {
        return profileApiService.getUserRatingFromApi(ProfileConstants.getUserRating(handle))
    }

    override suspend fun updateUserInfo(userInfoEntity: UserInfoEntity) {
        profileDao.updateUserInfo(userInfoEntity)
    }

    override suspend fun getUserStatusFromApi(handle: String): SubmissionDto {
        return profileApiService.getUserStatusFromApi(ProfileConstants.getUserStatus(handle))
    }
}