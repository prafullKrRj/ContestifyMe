package com.prafull.contestifyme.app.friendsFeature.di

import androidx.room.Room
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDB
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.app.friendsFeature.data.repo.FriendRepoImpl
import com.prafull.contestifyme.app.friendsFeature.domain.FriendRepo
import com.prafull.contestifyme.app.friendsFeature.ui.comparehandles.CompareViewModel
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.FriendsViewModel
import com.prafull.contestifyme.app.friendsFeature.ui.friendscreen.FriendScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val friendsModule = module {

    viewModel {
        FriendsViewModel()
    }
    viewModel {
        FriendScreenViewModel(get(), androidContext())
    }
    viewModel {
        CompareViewModel()
    }
    single {
        Room.databaseBuilder(androidContext(), FriendsDB::class.java, "friends_db").build()
    }
    single<FriendsDao> {
        get<FriendsDB>().friendsDao()
    }
    single<FriendRepo> {
        FriendRepoImpl()
    }
}