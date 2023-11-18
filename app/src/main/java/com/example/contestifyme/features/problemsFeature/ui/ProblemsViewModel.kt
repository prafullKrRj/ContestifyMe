package com.example.contestifyme.features.problemsFeature.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.problemsFeature.data.ProblemsRepository
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProblemsViewModel(
    private val problemsRepository: ProblemsRepository,

) : ViewModel() {
    var problemsUiState: ProblemsUiState by mutableStateOf(ProblemsUiState.Loading)
        private set

    init {
        getProblems()
    }
    private fun getProblems() {
        viewModelScope.launch {
            problemsUiState = ProblemsUiState.Loading
            problemsUiState = try {
                ProblemsUiState.Success(problemsRepository.getProblems())
            } catch (e: Exception) {
                ProblemsUiState.Error
            } catch (e: HttpException) {
                ProblemsUiState.Error
            }
        }
    }
}
sealed interface ProblemsUiState {
    object Loading : ProblemsUiState
    data class Success(val data: ProblemsDto) : ProblemsUiState
    object Error : ProblemsUiState
}