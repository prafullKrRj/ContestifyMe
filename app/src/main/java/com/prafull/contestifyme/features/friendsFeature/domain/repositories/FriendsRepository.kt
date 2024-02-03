package com.prafull.contestifyme.features.friendsFeature.domain.repositories

import com.prafull.contestifyme.commons.Response
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.domain.model.FriendsDetailsDto
import com.prafull.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun getFriendsDataFromApi(handles: List<String>): FriendsDetailsDto
    suspend fun updateFriendsDataInDb(friends: List<FriendsDataEntity>)
    fun getFriendsDataFromDb(): Flow<List<FriendsDataEntity>>
    suspend fun getRatingsFromApi(handles: List<String>): List<RatingDto>
    suspend fun getSubMissionFromApi(handles: List<String>): List<SubmissionDto>
}