package com.prafull.contestifyme.app.profileFeature.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.app.profileFeature.domain.model.ratingInfo.RatingResult
import com.prafull.contestifyme.app.profileFeature.domain.model.submissionsInfo.Submissions
import com.prafull.contestifyme.app.profileFeature.domain.model.userInfo.ProfileUserDto
import com.prafull.contestifyme.app.profileFeature.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


sealed class ProfileUiState {
    data object Initial : ProfileUiState()
    data object Loading : ProfileUiState()
    data class Success(val user: List<UserInfoEntity>) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

@SuppressLint("MutableCollectionMutableState")
class ProfileViewModel(
    val context: Context
) : ViewModel(), KoinComponent {

    private val profileRepository: ProfileRepository by inject()

    private val _profileUiState = MutableStateFlow<BaseClass<UserInfoEntity>>(BaseClass.Loading)
    val profileUiState = _profileUiState.asStateFlow()

    init {
        updateUserInfo()
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            profileRepository.getUserInfo().collectLatest { response ->
                _profileUiState.update { response }
                if (response is BaseClass.Error) {
                    Toast.makeText(context, response.exception.message, Toast.LENGTH_SHORT).show()
                    _profileUiState.update {
                        BaseClass.Success(profileRepository.getUserFromDatabase())
                    }
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
        programmingLanguage = this.programmingLanguage,
        verdict = if (this.verdict == "OK") "Accepted" else this.verdict,
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

fun ProfileUserDto.toUserInfoEntity(
    rating: List<UserRating>, status: List<UserSubmissions>
): UserInfoEntity {
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
