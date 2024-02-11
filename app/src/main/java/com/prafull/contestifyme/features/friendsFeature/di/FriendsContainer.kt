package com.prafull.contestifyme.features.friendsFeature.di

import android.app.Application
import android.content.Context
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.prafull.contestifyme.features.friendsFeature.data.source.FriendsRepositoryImpl
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FriendsModule {

    @Provides
    @Singleton
    fun provideApiService(): FriendsApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FriendsApiService::class.java)
    }
   /* @Provides
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
    */
    @Provides
    fun providesFriendsRepository(app: Application, api: FriendsApiService): FriendsRepository {
        return FriendsRepositoryImpl(
            friendsApiService = api,
            context = app
        )
    }
}