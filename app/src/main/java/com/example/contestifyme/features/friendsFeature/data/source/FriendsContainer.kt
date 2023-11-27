package com.example.contestifyme.features.friendsFeature.data.source

import android.content.Context
import com.example.contestifyme.constants.Constants.BASE_URL
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDB
import com.example.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface FriendsContainer {
    val friendsRepository: FriendsRepository
}
class FriendsContainerImpl(
    private val context: Context
) : FriendsContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create())
        .build()
    private val friendsApiService: FriendsApiService by lazy {
        retrofit.create(FriendsApiService::class.java)
    }
    override val friendsRepository: FriendsRepository by lazy {
        FriendsRepositoryImpl(
            friendsApiService,
            FriendsDB.getDatabase(context).friendsDao()
        )
    }

}