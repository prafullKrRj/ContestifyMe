package com.prafull.contestifyme.features.profileFeature.domain.repositories

import com.prafull.contestifyme.features.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.features.profileFeature.domain.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.domain.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.features.profileFeature.domain.model.userInfo.ProfileUserDto
import com.prafull.contestifyme.managers.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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
    fun getUserHandle(): String
}
