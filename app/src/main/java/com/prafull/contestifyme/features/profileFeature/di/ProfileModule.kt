package com.prafull.contestifyme.features.profileFeature.di

import android.app.Application
import androidx.room.Room
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDao
import com.prafull.contestifyme.features.profileFeature.data.local.ProfileDatabase
import com.prafull.contestifyme.features.profileFeature.data.remote.ProfileApiService
import com.prafull.contestifyme.features.profileFeature.data.repositories.ProfileRepositoryImpl
import com.prafull.contestifyme.features.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.managers.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideApiService(): ProfileApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideProfileDatabase(application: Application): ProfileDatabase {
        return Room.databaseBuilder(
            application,
            ProfileDatabase::class.java,
            "profile_database"
        ).build()
    }
    @Provides
    @Singleton
    fun providesProfileDao(appDatabase: ProfileDatabase): ProfileDao {
        return appDatabase.profileDao()
    }

    @Provides
    fun providesProfileRepository(dao: ProfileDao, api: ProfileApiService, sharedPrefManager: SharedPrefManager): ProfileRepository {
        return ProfileRepositoryImpl(
            api, dao, sharedPrefManager
        )
    }
    @Provides
    @Singleton
    fun providesSharedPref(application: Application): SharedPrefManager {
        return SharedPrefManager(application)
    }
}