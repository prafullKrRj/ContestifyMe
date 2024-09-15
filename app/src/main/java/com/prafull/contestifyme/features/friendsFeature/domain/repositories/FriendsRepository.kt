package com.prafull.contestifyme.features.friendsFeature.domain.repositories

import com.prafull.contestifyme.commons.Resource
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.domain.model.FriendsInfoResultDto
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    suspend fun getFromInternet(): Flow<Resource<List<FriendsInfoResultDto>>>
    suspend fun getSingleFriendFromInternet(handle: String): Flow<Resource<FriendsInfoResultDto>>
    suspend fun insertIntoDatabase(data: List<FriendsDataEntity>)
    fun getFromDatabase(): Flow<List<FriendsDataEntity>>
}