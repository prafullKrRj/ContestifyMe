package com.prafull.contestifyme.app.profileFeature.domain.repositories

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.network.model.UserSubmissions
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    /**
     *  User Info
     * */
    suspend fun getUserInfo(): Flow<BaseClass<UserData>>
    fun getUserSubmissions(): Flow<BaseClass<List<UserSubmissions>>>
    suspend fun getUserInfoFromLocal(): Flow<BaseClass<UserData>>
    fun getUserHandle(): String
    suspend fun deleteUserData()
}
