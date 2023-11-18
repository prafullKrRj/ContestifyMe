package com.example.contestifyme.features.profileFeature.data

import android.content.Context

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