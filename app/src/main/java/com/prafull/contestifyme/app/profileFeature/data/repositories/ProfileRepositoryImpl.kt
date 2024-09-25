package com.prafull.contestifyme.app.profileFeature.data.repositories

import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.app.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.app.profileFeature.domain.model.ratingInfo.RatingDto
import com.prafull.contestifyme.app.profileFeature.domain.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.app.profileFeature.domain.model.userInfo.ProfileUserDto
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.utils.managers.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ProfileRepositoryImpl : ProfileRepository, KoinComponent {


    private val profileApiService: ProfileApiService by inject()
    private val profileDao: ProfileDao by inject()
    private val sharedPrefManager: SharedPrefManager by inject()

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
        profileApiService.getUserInfoFromApi(
            com.prafull.contestifyme.app.profileFeature.constants.ProfileConstants.getUserInfo(
                handle
            )
        )


    override suspend fun getUserRatingFromApi(handle: String): RatingDto {
        return profileApiService.getUserRatingFromApi(
            com.prafull.contestifyme.app.profileFeature.constants.ProfileConstants.getUserRating(
                handle
            )
        )
    }

    override suspend fun updateUserInfo(userInfoEntity: UserInfoEntity) {
        profileDao.updateUserInfo(userInfoEntity)
    }

    override suspend fun getUserStatusFromApi(handle: String): SubmissionDto {
        return profileApiService.getUserStatusFromApi(
            com.prafull.contestifyme.app.profileFeature.constants.ProfileConstants.getUserStatus(
                handle
            )
        )
    }

}