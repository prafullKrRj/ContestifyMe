package com.prafull.contestifyme.features.friendsFeature.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.data.source.FriendsRepository
import com.prafull.contestifyme.features.profileFeature.ui.toUserRatingEntity
import com.prafull.contestifyme.features.profileFeature.ui.toUserStatusEntity
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
    var loading: Boolean by mutableStateOf(true)

    fun addFriends(handles: List<String>) {
        if (handles.isEmpty()) return
        viewModelScope.launch {
            try {
                handles.forEach { handle ->
                    val data = friendsRepository.getFriendsDataFromApi(listOf(handle))
                    val ratingInfo = friendsRepository.getRatingsFromApi(listOf(handle))
                    val submissionsInfo = friendsRepository.getSubMissionFromApi(listOf(handle))
                    friendsRepository.updateFriendsDataInDb(
                        data.result.map {
                            FriendsDataEntity(
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
                                ratingInfo = ratingInfo.first().result.map {ratingInfo ->
                                    ratingInfo.toUserRatingEntity()
                                },
                                subMissionInfo = submissionsInfo.first().submissions.map {submissionsInfo ->
                                    submissionsInfo.toUserStatusEntity()
                                }
                            )
                        }
                    )
                }
            } catch (e: HttpException) {
                Log.d("prafull", "http error")
            } catch (e: IOException) {
                Log.d("prafull", "io error")
            }
        }
    }
    fun updateDetails(handle: String) {
        loading = true
         viewModelScope.launch {
             try {
                val data = friendsRepository.getFriendsDataFromApi(listOf(handle))
                val ratingInfo = friendsRepository.getRatingsFromApi(listOf(handle))
                val submissionsInfo = friendsRepository.getSubMissionFromApi(listOf(handle))
                friendsRepository.updateFriendsDataInDb(
                    data.result.map {
                        FriendsDataEntity(
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
                            ratingInfo = ratingInfo.first().result.map {ratingInfo ->
                                ratingInfo.toUserRatingEntity()
                            },
                            subMissionInfo = submissionsInfo.first().submissions.map {submissionsInfo ->
                                submissionsInfo.toUserStatusEntity()
                            }
                        )
                    }
                )
                loading = false
            } catch (e: HttpException) {
                 loading = false
            } catch (e: IOException) {
                 loading = false
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