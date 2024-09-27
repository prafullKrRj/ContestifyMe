package com.prafull.contestifyme.app.friendsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.app.friendsFeature.data.FriendsRepoImpl
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDatabase
import com.prafull.contestifyme.app.friendsFeature.data.network.FriendApiService
import com.prafull.contestifyme.app.friendsFeature.domain.FriendsRepo
import com.prafull.contestifyme.app.friendsFeature.ui.FriendScreenViewModel
import com.prafull.contestifyme.app.friendsFeature.ui.FriendsViewModel
import com.prafull.contestifyme.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val friendsModule = module {

    viewModel {
        FriendsViewModel(androidContext())
    }
    viewModel {
        FriendScreenViewModel(get())
    }
    single<FriendsDatabase> {
        Room.databaseBuilder(androidContext(), FriendsDatabase::class.java, "friends_database")
            .build()
    }
    single<FriendsDao> {
        get<FriendsDatabase>().friendsDao()
    }
    single<FriendsRepo> {
        FriendsRepoImpl(androidContext())
    }
    single<FriendApiService> {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(FriendApiService::class.java)
    }
}