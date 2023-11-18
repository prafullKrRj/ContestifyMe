package com.example.contestifyme.ui.data

import android.content.Context
import com.example.contestifyme.constants.Constants.BASE_URL
import com.example.contestifyme.constants.Constants.URL
import com.example.contestifyme.ui.network.ApiService
import com.example.contestifyme.ui.network.model.userInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface OnBoardContainer {
    val onBoardRepository: OnBoardRepository
}
class OnBoardContainerImpl (
    private val context: Context
): OnBoardContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val onBoardRepository: OnBoardRepository by lazy {
        OnBoardRepositoryImpl(
            userDao = OnBoardDatabase.getDatabase(context).userDao(),
            apiService = retrofitService
        )
    }

}

interface OnBoardRepository {
    suspend fun upsertUser(appUser: AppUser)

    fun getUser(): Flow<List<AppUser>>

    suspend fun getUserValidation(handle: String): userInfo
}

class OnBoardRepositoryImpl (
    private val userDao: UserDao,
    private val apiService: ApiService
) : OnBoardRepository {
    override suspend fun upsertUser(appUser: AppUser) = userDao.upsertUser(appUser)

    override fun getUser(): Flow<List<AppUser>> = userDao.getUser()

    override suspend fun getUserValidation(handle: String): userInfo = apiService.getUserInfo(URL)
}