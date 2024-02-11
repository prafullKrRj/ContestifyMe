package com.prafull.contestifyme.features.contestsFeature.data.source

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.prafull.contestifyme.constants.Constants.BASE_URL
import com.prafull.contestifyme.features.contestsFeature.ContestsRepository
import com.prafull.contestifyme.features.contestsFeature.ContestsRepositoryImpl
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestDB
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.features.contestsFeature.data.remote.ContestsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ContestModule {

    @Provides
    @Singleton
    fun provideApiService(): ContestsApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContestsApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): ContestDB {
        return Room.databaseBuilder(
            application,
            ContestDB::class.java,
            "contests_db"
        ).build()
    }
    @Provides
    @Singleton
    fun providesContestDao(appDatabase: ContestDB): ContestsDao {
        return appDatabase.contestsDao()
    }

    @Provides
    fun provideHomeRepository(app: Application, api: ContestsApiService, dao: ContestsDao): ContestsRepository {
        return ContestsRepositoryImpl(
            context = app,
            contestsApi = api,
            contestsDao = dao
        )
    }
}