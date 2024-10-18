package com.prafull.contestifyme.app.contestsFeature.contestListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.contestsFeature.domain.repositories.ContestsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ContestsViewModel() : ViewModel(), KoinComponent {
    private val contestsRepository: ContestsRepository by inject()

    private val _state = MutableStateFlow<BaseClass<List<ContestsEntity>>>(BaseClass.Loading)
    val state = _state.asStateFlow()
    private var isError by mutableStateOf(false)

    init {
        if ((state.value !is BaseClass.Success || isError) || (state.value is BaseClass.Success && isError)) {
            updateContests()
        }
    }

    fun updateContests() {
        viewModelScope.launch(Dispatchers.IO) {
            contestsRepository.getContestsFromApi().collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
                        isError = true
                        contestsRepository.getContests().collectLatest { dbResponse ->
                            _state.update {
                                BaseClass.Success(dbResponse)
                            }
                        }
                    }

                    BaseClass.Loading -> {
                        _state.value = BaseClass.Loading
                    }

                    is BaseClass.Success -> {
                        isError = false
                        _state.update {
                            response
                        }
                    }
                }
            }
        }
    }
}
