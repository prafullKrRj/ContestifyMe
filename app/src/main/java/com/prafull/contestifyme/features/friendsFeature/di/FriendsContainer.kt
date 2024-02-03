package com.prafull.contestifyme.features.friendsFeature.di

import android.content.Context
import com.prafull.contestifyme.constants.Constants
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDB
import com.prafull.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.prafull.contestifyme.features.friendsFeature.data.source.FriendsRepositoryImpl
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface FriendsContainer {
    val friendsRepository: FriendsRepository
}
class FriendsContainerImpl(
    private val context: Context
) : FriendsContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create())
        .build()
    private val friendsApiService: FriendsApiService by lazy {
        retrofit.create(FriendsApiService::class.java)
    }
    override val friendsRepository: FriendsRepository by lazy {
        FriendsRepositoryImpl(
            friendsApiService,
            FriendsDB.getDatabase(context).friendsDao(),
            context
        )
    }

}