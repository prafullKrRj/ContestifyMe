package com.prafull.contestifyme.features.problemsFeature.di

import android.app.Application
import androidx.room.Room
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.problemsFeature.data.local.ProblemsDao
import com.prafull.contestifyme.features.problemsFeature.data.local.ProblemsDatabase
import com.prafull.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import com.prafull.contestifyme.features.problemsFeature.data.repositories.ProblemsRepositoryImpl
import com.prafull.contestifyme.features.problemsFeature.domain.repositories.ProblemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProblemsModule {

    @Provides
    @Singleton
        fun provideApiService(): ProblemsApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProblemsApiService::class.java)
    }
     @Provides
     @Singleton
     fun provideProblemsDatabase(application: Application): ProblemsDatabase {
         return Room.databaseBuilder(
             application,
             ProblemsDatabase::class.java,
             "problems_database"
         ).build()
     }
     @Provides
     @Singleton
     fun providesProblemsDao(appDatabase: ProblemsDatabase): ProblemsDao {
         return appDatabase.problemsDao()
     }

    @Provides
    fun providesProblemsRepository(dao: ProblemsDao, api: ProblemsApiService): ProblemsRepository {
        return ProblemsRepositoryImpl(
            api, dao
        )
    }
}