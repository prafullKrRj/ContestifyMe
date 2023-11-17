package com.example.contestifyme.friendsFeature.data

import android.content.Context
import com.example.contestifyme.friendsFeature.model.FriendsRepository

interface FriendsContainer {
    val friendsRepository: FriendsRepository
}
class FriendsContainerImpl(
    private val context: Context
) : FriendsContainer {
    override val friendsRepository: FriendsRepository
        get() = TODO("Not yet implemented")

}