package com.prafull.contestifyme.features.friendsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDB
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.prafull.contestifyme.features.friendsFeature.data.repositories.FriendsRepositoryImpl
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import com.prafull.contestifyme.features.friendsFeature.ui.FriendsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val friendsModule = module {
    viewModel { FriendsViewModel() }
    single<FriendsApiService> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FriendsApiService::class.java)
    }

    single<FriendsDB> {
        Room.databaseBuilder(
            androidApplication(),
            FriendsDB::class.java,
            "friends_db"
        ).build()
    }

    single<FriendsDao> {
        get<FriendsDB>().friendsDao()
    }

    single<FriendsRepository> {
        FriendsRepositoryImpl()
    }
}