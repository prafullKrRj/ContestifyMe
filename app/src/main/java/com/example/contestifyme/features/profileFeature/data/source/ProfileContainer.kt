package com.example.contestifyme.features.profileFeature.data.source

import android.content.Context
import com.example.contestifyme.constants.Constants.BASE_URL
import com.example.contestifyme.features.profileFeature.data.local.ProfileDatabase
import com.example.contestifyme.features.profileFeature.data.remote.ProfileApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ProfileContainer {
    val profileRepository: ProfileRepository
}
class ProfileContainerImpl(
    private val context: Context
) : ProfileContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val profileApiService: ProfileApiService by lazy {
        retrofit.create(ProfileApiService::class.java)
    }
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(
            profileApiService = profileApiService,
            profileDao = ProfileDatabase.getDatabase(context).profileDao()
        )
    }
}