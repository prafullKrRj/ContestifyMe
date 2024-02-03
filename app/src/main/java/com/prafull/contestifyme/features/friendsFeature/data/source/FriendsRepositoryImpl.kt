package com.prafull.contestifyme.features.friendsFeature.data.source

import android.content.Context
import android.widget.Toast
import com.prafull.contestifyme.commons.Response
import com.prafull.contestifyme.features.friendsFeature.FriendsConstants
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.prafull.contestifyme.features.friendsFeature.domain.model.FriendsDetailsDto
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import com.prafull.contestifyme.features.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FriendsRepositoryImpl (
    private val friendsApiService: FriendsApiService,
    private val friendsDao: FriendsDao,
    private val context: Context
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

    override suspend fun getRatingsFromApi(handles: List<String>): List<RatingDto> {
        return handles.map {
            friendsApiService.getUserRatingFromApi(ProfileConstants.getUserRating(it))
        }
    }

    override suspend fun getSubMissionFromApi(handles: List<String>): List<SubmissionDto> {
        return handles.map {
            friendsApiService.getUserStatusFromApi(ProfileConstants.getUserStatus(it))
        }
    }
}