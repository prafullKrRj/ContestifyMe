package com.prafull.contestifyme.features.problemsFeature.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.features.problemsFeature.data.source.ProblemsRepository
import com.prafull.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProblemsViewModel(
    private val problemsRepository: ProblemsRepository,

    ) : ViewModel() {
    var rating: MutableState<Int> = mutableStateOf(0)

    var problemsUiState: StateFlow<ProblemState> = problemsRepository.getProblemsFromDb()
        .map { ProblemState(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProblemState(emptyList()))
    private var isError by mutableStateOf(false)
    init {
        getProblems()
    }
    private fun getProblems() {
        viewModelScope.launch {
            try {
                val x = problemsRepository.getProblemsFromApi()
                problemsRepository.deleteAll()
                val list = mutableListOf<ProblemsEntity>()
                x.result.problems.forEachIndexed{ index, item->
                    list.add(
                        ProblemsEntity(
                            id = index+1,
                            rating = item.rating,
                            points = item.points,
                            unique = "${item.contestId}-${item.index}",
                            index = item.index,
                            tags = item.tags,
                            name = item.name
                        )
                    )
                }
                Log.d("prafull", "getProblems: $list")
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