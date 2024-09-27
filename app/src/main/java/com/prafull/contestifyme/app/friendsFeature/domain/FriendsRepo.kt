package com.prafull.contestifyme.app.friendsFeature.domain

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.onboard.model.UserResult
import kotlinx.coroutines.flow.Flow

interface FriendsRepo {

    fun getFriends(): Flow<BaseClass<List<UserResult>>>

    fun addUpdateFriend(friendData: FriendData): Flow<BaseClass<Unit>>
    suspend fun addFriend(handle: String): Flow<BaseClass<List<UserResult>>>
    fun getFriendData(handle: String): Flow<BaseClass<FriendData>>
}