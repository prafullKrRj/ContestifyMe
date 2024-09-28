package com.prafull.contestifyme.app.profileFeature.di

import androidx.room.Room
import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDatabase
import com.prafull.contestifyme.app.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.app.profileFeature.data.repositories.ProfileRepositoryImpl
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.app.profileFeature.ui.ProfileViewModel
import com.prafull.contestifyme.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val profileModule = module {
    viewModel { ProfileViewModel(androidContext()) }
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


    single<ProfileRepository> {
        ProfileRepositoryImpl()
    }
}