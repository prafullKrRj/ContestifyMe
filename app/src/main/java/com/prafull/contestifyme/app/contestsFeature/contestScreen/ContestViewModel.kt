package com.prafull.contestifyme.app.contestsFeature.contestScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.ContestRoutes
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.contestsFeature.domain.model.particularContest.ParticularContestDto
import com.prafull.contestifyme.app.contestsFeature.domain.repositories.ContestsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContestViewModel(
    val contest: ContestRoutes.ContestScreen,
) : ViewModel(), KoinComponent {

    private val repo: ContestsRepository by inject()
    private val _uiState = MutableStateFlow<BaseClass<ParticularContestDto>>(BaseClass.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getContestDetails()
    }

    fun getContestDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getParticularContestDetails(contest.contestId).collectLatest { response ->
                _uiState.update {
                    response
                }
            }
        }
    }
}