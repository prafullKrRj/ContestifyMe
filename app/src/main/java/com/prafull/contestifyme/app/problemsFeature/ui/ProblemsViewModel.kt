package com.prafull.contestifyme.app.problemsFeature.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.app.problemsFeature.domain.repositories.ProblemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProblemsViewModel : ViewModel(), KoinComponent {

    private val problemsRepository: ProblemsRepository by inject()
    private val context by inject<Context>()

    private val _uiState = MutableStateFlow<BaseClass<ProblemState>>(BaseClass.Loading)
    val uiState = _uiState.asStateFlow()
    private var isError by mutableStateOf(false)

    init {
        if (uiState.value !is BaseClass.Success || isError) {
            getProblemsFromInternet()
        }
    }

    private fun getProblemsFromInternet() {
        viewModelScope.launch {
            _uiState.value = BaseClass.Loading
            problemsRepository.getListOfProblems().collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        isError = true
                        problemsRepository.getProblemsFromDb().collectLatest { listEntity ->
                            if (listEntity.isEmpty()) {
                                _uiState.update { BaseClass.Error(Exception("No Data Found")) }
                            } else {
                                _uiState.update { BaseClass.Success(ProblemState(listEntity)) }
                            }
                        }
                        Toast.makeText(context, "Error Loading New Problems", Toast.LENGTH_SHORT)
                            .show()
                    }

                    BaseClass.Loading -> {
                        isError = false
                        _uiState.update { BaseClass.Loading }
                    }

                    is BaseClass.Success -> {
                        isError = false
                        _uiState.update { BaseClass.Success(ProblemState(response.data)) }
                    }
                }
            }
        }
    }
}

data class ProblemState(
    var entity: List<ProblemsEntity> = emptyList()
)