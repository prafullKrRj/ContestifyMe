package com.example.contestifyme.features.friendsFeature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.example.contestifyme.features.friendsFeature.data.source.FriendsRepository
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
        getFriends(friendsUiState.value.friends.map { it.handle })
    }
    fun getFriends(handle: List<String>) {
        if (handle.isEmpty()) return
        viewModelScope.launch {
            try {
                val data = friendsRepository.getFriendsDataFromApi(handle)
                friendsRepository.updateFriendsDataInDb(data.result.map {
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
                    )
                })
            } catch (e: HttpException) {
                Log.d("prafull", "http exception")
            } catch (e: IOException) {
                Log.d("prafull", "iO exception")
            }
        }
    }
}
data class FriendsUiState(
    val friends: List<FriendsDataEntity> = emptyList()
)