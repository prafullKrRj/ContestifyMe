package com.prafull.contestifyme.startOnBoard.data

import android.content.Context
import com.prafull.contestifyme.commons.Resource
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.constants.Constants.BASE_URL
import com.prafull.contestifyme.managers.SharedPrefManager
import com.prafull.contestifyme.startOnBoard.network.ApiService
import com.prafull.contestifyme.startOnBoard.network.model.userInfo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface OnBoardContainer {
    val onBoardRepository: OnBoardRepository
}

class OnBoardContainerImpl(
    private val context: Context
) : OnBoardContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    private val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    override val onBoardRepository: OnBoardRepository by lazy {
        OnBoardRepositoryImpl(
            apiService = retrofitService,
            sharedPrefManager = sharedPrefManager
        )
    }

}

interface OnBoardRepository {
    suspend fun getUserValidation(handle: String): Flow<Resource<userInfo>>
}

class OnBoardRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPrefManager: SharedPrefManager
) : OnBoardRepository {
    override suspend fun getUserValidation(handle: String): Flow<Resource<userInfo>> {
        return callbackFlow {
            trySend(Resource.Loading)
            try {
                val userInfo = apiService.getUserInfo(Constants.getUserStart(handle))
                sharedPrefManager.setLoginUserHandle(handle)
                sharedPrefManager.setLoggedIn(true)
                trySend(Resource.Success(userInfo))
            } catch (e: Exception) {
                trySend(Resource.Error(e))
            }
            awaitClose { }
        }
    }
}