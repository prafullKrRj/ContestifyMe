package com.prafull.contestifyme.features.profileFeature.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.features.profileFeature.domain.repositories.ProfileRepository
import com.prafull.contestifyme.features.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.features.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.features.profileFeature.domain.model.ratingInfo.RatingResult
import com.prafull.contestifyme.features.profileFeature.domain.model.submissionsInfo.Submissions
import com.prafull.contestifyme.features.profileFeature.domain.model.userInfo.ProfileUserDto
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject


sealed class ProfileUiState {
    object Initial: ProfileUiState()
    object Loading: ProfileUiState()
    data class Success(val user: List<UserInfoEntity>): ProfileUiState()
    data class Error(val message: String): ProfileUiState()
}
@HiltViewModel
@SuppressLint("MutableCollectionMutableState")
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
): ViewModel() {

    val dataFromDb: StateFlow<ProfileUiState.Success> = profileRepository.getUserInfo()
        .map {
            ProfileUiState.Success(
                user = it
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUiState.Success(emptyList())
        )



    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Initial)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    init {
        updateUserInfo()
    }
    fun updateUserInfo() {
        viewModelScope.launch {
            _profileUiState.update {
                ProfileUiState.Loading
            }
            try {
                val userInfo = profileRepository.getUserInfoFromApi(profileRepository.getUserHandle())
                val ratingInfo = profileRepository.getUserRatingFromApi(profileRepository.getUserHandle())
                val submissionInfo = profileRepository.getUserStatusFromApi(profileRepository.getUserHandle())
                val user = userInfo.toUserInfoEntity(
                    rating = ratingInfo.result.map {
                        it.toUserRatingEntity()
                    },
                    status = submissionInfo.submissions.map {
                        it.toUserStatusEntity()
                    }
                )
                _profileUiState.update {
                    ProfileUiState.Success(
                        user = listOf(user)
                    )
                }
                profileRepository.insertUser(user)
            } catch (e: HttpException) {
                Log.d("check", "updateUserInfo: HttpError")
                _profileUiState.update {
                    ProfileUiState.Error(
                        message = e.message()
                    )
                }
            } catch (e: IOException) {
                Log.d("check", "updateUserInfo: IOException")
                _profileUiState.update {
                    ProfileUiState.Error(
                        message = e.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}
/**
 *          Extension Functions
 * */
fun Submissions.toUserStatusEntity(): UserSubmissions {
    return UserSubmissions(
        id = this.id,
        name = this.problem.name,
        verdict =  if (this.verdict == "OK") "Accepted" else this.verdict,
        time = this.timeConsumedMillis,
        contestId = this.contestId,
        index = this.problem.index,
        rating = this.problem.rating,
        tags = this.problem.tags,
    )
}
fun RatingResult.toUserRatingEntity(): UserRating {
    return UserRating(
        contestId = this.contestId,
        contestName = this.contestName,
        handle = this.handle,
        newRating = this.newRating,
        oldRating = this.oldRating,
        rank = this.rank,
        ratingUpdateTimeSeconds = this.ratingUpdateTimeSeconds
    )
}

fun ProfileUserDto.toUserInfoEntity(rating: List<UserRating>, status: List<UserSubmissions>): UserInfoEntity {
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
        titlePhoto = this.result[0].titlePhoto,
        ratingInfo = rating,
        subMissionInfo = status
    )
}
