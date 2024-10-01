package com.prafull.contestifyme.app.problemsFeature.ui.acsmsguru

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru.AcmsguruDto
import com.prafull.contestifyme.app.problemsFeature.domain.repositories.ProblemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AcmsGuruViewModel : ViewModel(), KoinComponent {
    private val repo: ProblemsRepository by inject()
    private val _state = MutableStateFlow<BaseClass<AcmsguruDto>>(BaseClass.Loading)
    val state = _state.asStateFlow()

    init {
        getAcmsGuruProblems()
    }

    fun getAcmsGuruProblems() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BaseClass.Loading
            repo.getAcmsGuruProblems().collectLatest { response ->
                _state.update { response }
            }
        }
    }
}