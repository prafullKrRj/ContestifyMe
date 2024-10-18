package com.prafull.contestifyme.app.profileFeature.data.repositories

import android.util.Log
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.network.CodeForcesApi
import com.prafull.contestifyme.network.CodeforcesUtil
import com.prafull.contestifyme.network.model.UserSubmissions
import com.prafull.contestifyme.onboard.OnBoardApiService
import com.prafull.contestifyme.utils.managers.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ProfileRepositoryImpl : ProfileRepository, KoinComponent {

    private val codeforcesApi by inject<CodeForcesApi>()
    private val profileDao: ProfileDao by inject()
    private val sharedPrefManager: SharedPrefManager by inject()
    private val onBoardApi by inject<OnBoardApiService>()
    override fun getUserHandle(): String = sharedPrefManager.getLoginUserHandle()

    override suspend fun deleteUserData() {
        profileDao.deleteUserData()
    }

    override suspend fun getUserInfo(): Flow<BaseClass<UserData>> = flow {
        try {
            val handle = getUserHandle()
            Log.d("ProfileRepositoryImpl", "getUserInfo: ${getUserHandle()}")
            val userSubmissions =
                codeforcesApi.getStatus(CodeforcesUtil.getUserSubmissionsUrl(handle))
            val userRating =
                codeforcesApi.getUserRating(CodeforcesUtil.getUserRatingUrl(handle))
            Log.d("ProfileRepositoryImpl", "getUserSubmissions: $userSubmissions")
            val userInfo = codeforcesApi.getUserInfo(CodeforcesUtil.getUserInfoUrl(handle))
            Log.d("ProfileRepositoryImpl", "getUserInfo: $userInfo")
            val userData = UserData(
                handle = getUserHandle(),
                usersInfo = userInfo.result[0],
                userSubmissions = userSubmissions.toUserStatus(),
                userRating = userRating.toUserRatings()
            )
            profileDao.insertUserData(userData)
            emit(BaseClass.Success(userData))
        } catch (e: Exception) {
            Log.d("ProfileRepositoryImpl", "getUserInfo: ${e.message}")
            emit(BaseClass.Error(e))
            e.printStackTrace()
        }
    }

    override fun getUserSubmissions(): Flow<BaseClass<List<UserSubmissions>>> {
        return flow {
            try {
                val userSubmissions = profileDao.getUserSubmissions()
                Log.d("SubmissionsTesting", "getUserSubmissions: $userSubmissions")
                emit(BaseClass.Success(userSubmissions.map { it.toUserSubmissions() }))
            } catch (e: Exception) {
                emit(BaseClass.Error(e))
                e.printStackTrace()
            }
        }
    }

    override suspend fun getUserInfoFromLocal(): Flow<BaseClass<UserData>> {
        return flow {
            try {
                val userData = profileDao.getUserData(getUserHandle())
                emit(BaseClass.Success(userData))
            } catch (e: Exception) {
                emit(BaseClass.Error(e))
                e.printStackTrace()
            }
        }
    }
}