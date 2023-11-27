package com.example.contestifyme.features.friendsFeature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.example.contestifyme.features.friendsFeature.data.source.FriendsRepository
import com.example.contestifyme.features.profileFeature.model.UserRating
import com.example.contestifyme.features.profileFeature.model.UserSubmissions
import com.example.contestifyme.features.profileFeature.ui.toUserRatingEntity
import com.example.contestifyme.features.profileFeature.ui.toUserStatusEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FriendsViewModel(
    private val friendsRepository: FriendsRepository,

) : ViewModel() {

    var friendsUiState: StateFlow<FriendsUiState> = friendsRepository.getFriendsDataFromDb().map {
        FriendsUiState(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FriendsUiState(emptyList()))

    init {
        addFriends(friendsUiState.value.friends.map { it.handle })
    }
    fun addFriends(handles: List<String>) {
        if (handles.isEmpty()) return
        viewModelScope.launch {
            try {
                val data = friendsRepository.getFriendsDataFromApi(handles)
                val rating: List<List<UserRating>> = friendsRepository.getRatingsFromApi(handles).map {ratingDto ->
                    ratingDto.result.map {
                        it.toUserRatingEntity()
                    }
                }
                val submissions: List<List<UserSubmissions>> = friendsRepository.getSubMissionFromApi(handles).map {submissionDto ->
                    submissionDto.submissions.map {
                        it.toUserStatusEntity()
                    }
                }
                friendsRepository.updateFriendsDataInDb(
                    data.result.mapIndexed { index, it ->
                        FriendsDataEntity(
                            id = 0,
                            handle = it.handle,
                            avatar = it.avatar,
                            contribution = it.contribution,
                            country = it.country,
                            name = it.firstName,
                            friendOfCount = it.friendOfCount,
                            lastOnlineTimeSeconds = it.lastOnlineTimeSeconds,
                            maxRank = it.maxRank,
                            maxRating = it.maxRating,
                            organization = it.organization,
                            rank = it.rank,
                            rating = it.rating,
                            registrationTimeSeconds = it.registrationTimeSeconds,
                            titlePhoto = it.titlePhoto,
                            ratingInfo = rating[index],
                            subMissionInfo = submissions[index]
                        )
                    }
                )
            } catch (e: HttpException) {
                Log.d("prafull", "http error")
            } catch (e: IOException) {
                Log.d("prafull", "io error")
            }
        }
    }

    fun getFriend(handle: String): FriendsDataEntity {
        return friendsUiState.value.friends.first { it.handle == handle }
    }
}
data class FriendsUiState(
    val friends: List<FriendsDataEntity> = emptyList(),
    var loading: Boolean = true
)