package com.example.contestifyme.features.friendsFeature.data.source

import android.content.Context
import androidx.room.Dao
import com.example.contestifyme.features.friendsFeature.FriendsConstants
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDao
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.example.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.example.contestifyme.features.friendsFeature.model.FriendsDetailsDto
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun getFriendsDataFromApi(handles: List<String>): FriendsDetailsDto
    suspend fun updateFriendsDataInDb(friends: List<FriendsDataEntity>)
    fun getFriendsDataFromDb(): Flow<List<FriendsDataEntity>>
}

class FriendsRepositoryImpl (
    private val friendsApiService: FriendsApiService,
    private val friendsDao: FriendsDao
) : FriendsRepository {
    override suspend fun getFriendsDataFromApi(handles: List<String>): FriendsDetailsDto {
        return friendsApiService.getFriendsData(FriendsConstants.getUserInfo(handles))
    }
    override suspend fun updateFriendsDataInDb(friends: List<FriendsDataEntity>) {
        friendsDao.upsert(friends)
    }

    override fun getFriendsDataFromDb(): Flow<List<FriendsDataEntity>> {
        return friendsDao.getAllFriends()
    }
}