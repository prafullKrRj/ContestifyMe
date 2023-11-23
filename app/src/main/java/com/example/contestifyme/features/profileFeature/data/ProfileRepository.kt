package com.example.contestifyme.features.profileFeature.data

import com.example.contestifyme.features.profileFeature.constants.ProfileConstants
import com.example.contestifyme.features.profileFeature.data.local.ProfileDao
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.local.entities.UserRating
import com.example.contestifyme.features.profileFeature.data.local.entities.UserStatus
import com.example.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.example.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.example.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import com.example.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface ProfileRepository {

    /**
     *  User Info
     * */
    suspend fun insertUser(userInfoEntity: UserInfoEntity)
    fun getUserInfo(): Flow<List<UserInfoEntity>>
    suspend fun getUserInfoFromApi(handle: String): ProfileUserDto

    /**
     *  User Rating
     * */
    suspend fun insertUserRating(userRating: List<UserRating>)
    fun getUserRating(): Flow<List<UserRating>>
    suspend fun getUserRatingFromApi(handle: String): RatingDto

    /**
     *  User Status
     * */
    suspend fun insertUserStatus(userStatus: List<UserStatus>)
    fun getUserStatus(): Flow<List<UserStatus>>
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

    /**
     *  User Rating
     * */
    override suspend fun insertUserRating(userRating: List<UserRating>) {
        profileDao.insertUserRating(userRating)
    }

    override fun getUserRating(): Flow<List<UserRating>> {
        return profileDao.getUserRating()
    }

    override suspend fun getUserRatingFromApi(handle: String): RatingDto {
        return profileApiService.getUserRatingFromApi(ProfileConstants.getUserRating(handle))
    }

    /**
     *  User Status
     * */
    override suspend fun insertUserStatus(userStatus: List<UserStatus>) {
        profileDao.insertUserStatus(userStatus)
    }

    override fun getUserStatus(): Flow<List<UserStatus>> {
        return profileDao.getUserStatus()
    }

    override suspend fun getUserStatusFromApi(handle: String): SubmissionDto {
        return profileApiService.getUserStatusFromApi(ProfileConstants.getUserStatus(handle))
    }
}
data class Mapper(
    var userInfoEntity: List<UserInfoEntity>,
    var userRating: List<UserRating>,
    var userStatus: List<UserStatus>
)