package com.prafull.contestifyme.features.profileFeature.data.repositories

import com.prafull.contestifyme.features.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.features.profileFeature.domain.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.domain.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.features.profileFeature.domain.model.userInfo.ProfileUserDto
import com.prafull.contestifyme.features.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.managers.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor (
    private val profileApiService: ProfileApiService,
    private val profileDao: ProfileDao,
    private val sharedPrefManager: SharedPrefManager
) : ProfileRepository {


    override fun getUserHandle(): String = sharedPrefManager.getLoginUserHandle()
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