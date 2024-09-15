package com.prafull.contestifyme.features.profileFeature.di

import androidx.room.Room
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDatabase
import com.prafull.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.features.profileFeature.data.repositories.ProfileRepositoryImpl
import com.prafull.contestifyme.features.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.features.profileFeature.ui.ProfileViewModel
import com.prafull.contestifyme.managers.SharedPrefManager
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val profileModule = module {
    viewModel { ProfileViewModel() }
    single<ProfileApiService> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApiService::class.java)
    }

    single<ProfileDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            ProfileDatabase::class.java,
            "profile_database"
        ).build()
    }

    single<ProfileDao> {
        get<ProfileDatabase>().profileDao()
    }

    single<SharedPrefManager> {
        SharedPrefManager(androidApplication())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl()
    }
}