package com.example.contestifyme.features.friendsFeature.data.source

import com.example.contestifyme.features.friendsFeature.FriendsConstants
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDao
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.example.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.example.contestifyme.features.friendsFeature.model.FriendsDetailsDto
import com.example.contestifyme.features.profileFeature.constants.ProfileConstants
import com.example.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.example.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun getFriendsDataFromApi(handles: List<String>): FriendsDetailsDto
    suspend fun updateFriendsDataInDb(friends: List<FriendsDataEntity>)
    fun getFriendsDataFromDb(): Flow<List<FriendsDataEntity>>
    suspend fun getRatingsFromApi(handles: String): RatingDto
    suspend fun getSubMissionFromApi(handles: String): SubmissionDto
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

    override suspend fun getRatingsFromApi(handles: String): RatingDto {
        return friendsApiService.getUserRatingFromApi(ProfileConstants.getUserRating(handles))
    }
    override suspend fun getSubMissionFromApi(handles: String): SubmissionDto {
        return friendsApiService.getUserStatusFromApi(ProfileConstants.getUserStatus(handles))
    }
}