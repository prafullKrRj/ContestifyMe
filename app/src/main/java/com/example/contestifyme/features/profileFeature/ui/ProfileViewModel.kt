package com.example.contestifyme.features.profileFeature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.profileFeature.data.ProfileRepository
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val handle: String
): ViewModel() {

    val profileUiState: StateFlow<ProfileUiState> = profileRepository.getUserInfo()
        .map {
            ProfileUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUiState(emptyList())
        )
    init {
        updateUserInfo()
    }

    private fun updateUserInfo() {
        viewModelScope.launch {
            try {
                val userInfo = profileRepository.getUserInfoFromApi(handle)
                profileRepository.insertUser(
                    userInfo.toUserInfoEntity()
                )
            } catch (e: HttpException) {
                Log.d("check", "updateUserInfo: HttpError")
            } catch (e: IOException) {
                Log.d("check", "updateUserInfo: IOError")
            }
        }
    }
}
fun ProfileUserDto.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        handle = this.result[0].handle,
        avatar = this.result[0].avatar,
        contribution = this.result[0].contribution,
        country = this.result[0].country,
        name = this.result[0].firstName,
        friendOfCount = this.result[0].friendOfCount,
        lastOnlineTimeSeconds = this.result[0].lastOnlineTimeSeconds,
        maxRank = this.result[0].maxRank,
        maxRating = this.result[0].maxRating,
        organization = this.result[0].organization,
        rank = this.result[0].rank,
        rating = this.result[0].rating,
        registrationTimeSeconds = this.result[0].registrationTimeSeconds,
        titlePhoto = this.result[0].titlePhoto
    )
}
data class ProfileUiState(
    var user: List<UserInfoEntity> = emptyList(),
)