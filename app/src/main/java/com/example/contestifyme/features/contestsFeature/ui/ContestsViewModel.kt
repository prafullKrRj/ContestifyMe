package com.example.contestifyme.features.contestsFeature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.contestsFeature.ContestsRepository
import com.example.contestifyme.features.contestsFeature.data.local.ContestsEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


data class ContestUiState(
    val isLoading: Boolean = false,
    val contests: List<ContestsEntity> = emptyList(),
    val error: Boolean = false,
)
class ContestsViewModel(
    private val contestsRepository: ContestsRepository,
) : ViewModel() {
    val state: StateFlow<ContestUiState> = contestsRepository.getContests().map {list ->
        ContestUiState(false, list, false)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ContestUiState(false, emptyList(), false)
    )
    init {
        getContests()
    }
    private fun getContests() {
        viewModelScope.launch {
            try {
                val contests = contestsRepository.getContestsFromApi()
                contests.result.forEach {
                    contestsRepository.insertContests(
                        ContestsEntity(
                            it.id,
                            it.durationSeconds,
                            it.frozen,
                            it.name,
                            it.phase,
                            it.relativeTimeSeconds,
                            it.startTimeSeconds,
                            it.type
                        )
                    )
                }
            } catch (e: HttpException) {
                Log.d("prafull", "HttpException")
            } catch (e: IOException) {
                Log.d("prafull", "IOException")
            }
        }
    }
}
