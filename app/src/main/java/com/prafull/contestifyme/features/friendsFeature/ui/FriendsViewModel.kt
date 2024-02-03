package com.prafull.contestifyme.features.friendsFeature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.domain.model.FriendsDetailsDto
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FriendsViewModel(
    private val friendsRepository: FriendsRepository,

    ) : ViewModel() {

    val dataFromDb: StateFlow<FriendsUiState.Success> = friendsRepository.getFriendsDataFromDb().map {
         FriendsUiState.Success(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FriendsUiState.Success(emptyList()))
    private val _uiState = MutableStateFlow<FriendsUiState>(FriendsUiState.Initial)
    val uiState = _uiState.asStateFlow()
    init {
        Log.d("handlesdb", "${dataFromDb.value.data.map { it.handle }}")
        addFriends(dataFromDb.value.data.map { it.handle })
    }
    fun addFriends(handles: List<String> = dataFromDb.value.data.map { it.handle }) {
        if (handles.isEmpty()) return
        Log.d("handles", "$handles")
        viewModelScope.launch {
            _uiState.update {
                FriendsUiState.Loading
            }
            try {
                val list = mutableListOf<FriendsDataEntity>()
                handles.forEach { handle ->
                    list.add(friendsRepository.getFriendsDataFromApi(listOf(handle)).toFriendsDataEntity())
                }
                try {
                    friendsRepository.updateFriendsDataInDb(list)
                } catch (e: Exception) {
                    Log.d("handles", "$e")
                }
                Log.d("handles", "${list[0].avatar}")
                _uiState.update {
                    FriendsUiState.Success(list.toList())
                }
            } catch (e: HttpException) {
                _uiState.update {
                    FriendsUiState.Error(e)
                }
            } catch (e: IOException) {
                _uiState.update {
                    FriendsUiState.Error(e)
                }
            }
        }
    }
    fun addFriend(handle: String) {

    }
    fun getFriend(handle: String): FriendsDataEntity {
        return dataFromDb.value.data.first { it.handle == handle }
    }
}
sealed class FriendsUiState {
    object Initial: FriendsUiState()
    object Loading: FriendsUiState()
    data class Success(val data: List<FriendsDataEntity>): FriendsUiState()
    data class Error(val error: Exception): FriendsUiState()
}
fun FriendsDetailsDto.toFriendsDataEntity(): FriendsDataEntity {
    return FriendsDataEntity(
        handle = this.result.first().handle,
        avatar = this.result.first().avatar,
        contribution = this.result.first().contribution,
        country = this.result.first().country,
        name = this.result.first().firstName,
        lastOnlineTimeSeconds = this.result.first().lastOnlineTimeSeconds,
        maxRank = this.result.first().maxRank,
        maxRating = this.result.first().maxRating,
        rank = this.result.first().rank,
        rating = this.result.first().rating,
        registrationTimeSeconds = this.result.first().registrationTimeSeconds,
        titlePhoto = this.result.first().titlePhoto
    )
}
