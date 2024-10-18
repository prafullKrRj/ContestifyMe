package com.prafull.contestifyme.app.profileFeature.di

import androidx.room.Room
import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDatabase
import com.prafull.contestifyme.app.profileFeature.data.repositories.ProfileRepositoryImpl
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.app.profileFeature.ui.ProfileViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel { ProfileViewModel() }

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