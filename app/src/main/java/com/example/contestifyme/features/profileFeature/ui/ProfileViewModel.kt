package com.example.contestifyme.features.profileFeature.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.contestifyme.features.profileFeature.constants.ProfileConstants
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import com.example.contestifyme.features.profileFeature.data.source.ProfileRepository
import com.example.contestifyme.features.profileFeature.model.UserRating
import com.example.contestifyme.features.profileFeature.model.UserSubmissions
import com.example.contestifyme.features.profileFeature.model.ratingInfo.RatingResult
import com.example.contestifyme.features.profileFeature.model.submissionsInfo.Submissions
import com.example.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


data class ProfileUiState(
    var user: List<UserInfoEntity> = emptyList()
)
@SuppressLint("MutableCollectionMutableState")
class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val handle: String
): ViewModel() {

    val profileUiState: StateFlow<ProfileUiState> = profileRepository.getUserInfo()
        .map {
            ProfileUiState(
                user = it
            )
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
                val ratingInfo = profileRepository.getUserRatingFromApi(handle)
                val submissionInfo = profileRepository.getUserStatusFromApi(handle)
                profileRepository.insertUser(
                    userInfo.toUserInfoEntity(
                        rating = ratingInfo.result.map {
                            it.toUserRatingEntity()
                        },
                        status = submissionInfo.submissions.map {
                            it.toUserStatusEntity()
                        }
                    )
                )
            } catch (e: HttpException) {
                Log.d("check", "updateUserInfo: HttpError")
            } catch (e: IOException) {
                Log.d("check", "updateUserInfo: IOError")
            }
        }
    }


    fun getVerdicts(): HashMap<String, Int> {
        if (profileUiState.value.user.isEmpty()) {
            return hashMapOf()
        }
        val verdictsMap: HashMap<String, Int> by mutableStateOf(hashMapOf())
        profileUiState.value.user[0].subMissionInfo.forEach {
            if (verdictsMap.containsKey(it.verdict)) {
                verdictsMap[it.verdict] = verdictsMap[it.verdict]!! + 1
            } else {
                verdictsMap[it.verdict] = 1
            }
        }
        return verdictsMap
    }
    private fun getList(verdicts: HashMap<String, Int>): List<PieChartData.Slice> {
        val list: List<PieChartData.Slice> = verdicts.keys.map {
            PieChartData.Slice(
                value = verdicts[it]!!.toFloat(),
                color = ProfileConstants.colors[it.uppercase()]!!.first,
                label = verdicts[it].toString(),
            )
        }
        return list
    }
    fun pieChartData(verdicts: HashMap<String, Int>): PieChartData {
        return PieChartData(
            slices = getList(verdicts),
            plotType = PlotType.Pie,
        )
    }
    fun getQuestionSolvedByIndexData(): HashMap<String, Int> {
        if (profileUiState.value.user.isEmpty()) {
            return hashMapOf()
        }
        val questionSolvedByIndex: HashMap<String, Int> by mutableStateOf(hashMapOf())
        profileUiState.value.user[0].subMissionInfo.forEach {
            if (questionSolvedByIndex.containsKey(it.index)) {
                questionSolvedByIndex[it.index] = questionSolvedByIndex[it.index]!! + 1
            } else {
                questionSolvedByIndex[it.index] = 1
            }
        }
        return questionSolvedByIndex
    }
}
fun Submissions.toUserStatusEntity(): UserSubmissions {
    return UserSubmissions(
        id = this.id,
        name = this.problem.name,
        verdict =  if (this.verdict == "OK") "Accepted" else this.verdict,
        time = this.timeConsumedMillis,
        contestId = this.contestId,
        index = this.problem.index,
        rating = this.problem.rating
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
object GetChartData {
    fun pieChartConfig(): PieChartConfig {
        return PieChartConfig(
            labelType = PieChartConfig.LabelType.VALUE,
            isAnimationEnable = true,
            showSliceLabels = true,
            animationDuration = 1500,
            isClickOnSliceEnabled = false
        )
    }
}