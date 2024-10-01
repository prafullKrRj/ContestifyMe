package com.prafull.contestifyme.app.friendsFeature.domain

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import kotlinx.coroutines.flow.Flow


interface FriendRepo {


    suspend fun getFriendsDataFromDB(): List<UserInfoEntity>
    fun getSingleFriendData(handle: String): Flow<BaseClass<UserData>>

    fun updateFriends(): Flow<BaseClass<List<UserInfoEntity>>>
    fun insertFriend(handle: String): Flow<BaseClass<List<UserInfoEntity>>>

    suspend fun getFriendDataFromDb(handle: String): UserInfoEntity?
}