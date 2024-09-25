package com.prafull.contestifyme.app.friendsFeature.di

import com.prafull.contestifyme.app.friendsFeature.ui.FriendsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val friendsModule = module {

    viewModel {
        FriendsViewModel()
    }
}