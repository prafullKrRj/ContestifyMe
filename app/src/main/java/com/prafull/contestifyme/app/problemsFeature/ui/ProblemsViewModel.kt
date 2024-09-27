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
import retrofit2.HttpException
import java.io.IOException

class ProblemsViewModel(
    private val context: Context
) : ViewModel(), KoinComponent {

    private val problemsRepository: ProblemsRepository by inject()


    private val _uiState = MutableStateFlow<BaseClass<ProblemState>>(BaseClass.Loading)
    val uiState = _uiState.asStateFlow()
    private var isError by mutableStateOf(false)

    init {
        getProblemsFromInternet()
    }

    fun getProblemsFromInternet() {
        viewModelScope.launch {
            problemsRepository.getListOfProblems().collectLatest { response ->
                when (response) {
                    is BaseClass.Error -> {
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

                    BaseClass.Initial -> TODO()
                    BaseClass.Loading -> {
                        _uiState.update { BaseClass.Loading }
                    }

                    is BaseClass.Success -> {
                        _uiState.update { BaseClass.Success(ProblemState(response.data)) }
                    }
                }
            }
        }
    }

    private fun getProblems() {
        viewModelScope.launch {
            try {
                val x = problemsRepository.getProblemsFromApi()
                problemsRepository.deleteAll()
                val list = mutableListOf<ProblemsEntity>()
                x.result.problems.forEachIndexed { index, item ->
                    list.add(
                        ProblemsEntity(
                            rating = item.rating,
                            points = item.points,
                            unique = "${item.contestId}-${item.index}",
                            index = item.index,
                            tags = item.tags,
                            name = item.name
                        )
                    )
                }
                isError = false
                problemsRepository.upsertProblems(list)
            } catch (e: HttpException) {
                isError = true
            } catch (e: IOException) {
                isError = true
            }
        }
    }

}

data class ProblemState(
    var entity: List<ProblemsEntity> = emptyList()
)