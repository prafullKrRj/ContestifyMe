package com.example.contestifyme.features.friendsFeature.data

import android.content.Context
import com.example.contestifyme.features.friendsFeature.model.FriendsRepository
import com.example.contestifyme.features.friendsFeature.model.FriendsRepositoryImpl

interface FriendsContainer {
    val friendsRepository: FriendsRepository
}
class FriendsContainerImpl(
    private val context: Context
) : FriendsContainer {
    override val friendsRepository: FriendsRepository by lazy {
        FriendsRepositoryImpl()
    }

}