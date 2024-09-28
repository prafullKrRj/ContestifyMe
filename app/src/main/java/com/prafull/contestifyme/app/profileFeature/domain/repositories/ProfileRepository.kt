package com.prafull.contestifyme.app.profileFeature.domain.repositories

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.app.profileFeature.domain.model.ratingInfo.RatingDto
import com.prafull.contestifyme.app.profileFeature.domain.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.app.profileFeature.domain.model.userInfo.ProfileUserDto
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    /**
     *  User Info
     * */
    suspend fun insertUser(userInfoEntity: UserInfoEntity)
    fun getUserInfo(): Flow<BaseClass<UserInfoEntity>>
    suspend fun getUserInfoFromApi(handle: String): ProfileUserDto

    suspend fun getUserRatingFromApi(handle: String): RatingDto
    suspend fun updateUserInfo(userInfoEntity: UserInfoEntity)
    suspend fun getUserStatusFromApi(handle: String): SubmissionDto
    fun getUserHandle(): String
    suspend fun getUserFromDatabase(): UserInfoEntity
}
