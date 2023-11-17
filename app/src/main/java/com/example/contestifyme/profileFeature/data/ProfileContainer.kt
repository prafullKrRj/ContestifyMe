package com.example.contestifyme.profileFeature.data

import android.content.Context
import com.example.contestifyme.profileFeature.model.ProfileRepository
import com.example.contestifyme.profileFeature.model.ProfileRepositoryImpl

interface ProfileContainer {
    val profileRepository: ProfileRepository
}
class ProfileContainerImpl(
    private val context: Context
) : ProfileContainer {
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl()
    }
}