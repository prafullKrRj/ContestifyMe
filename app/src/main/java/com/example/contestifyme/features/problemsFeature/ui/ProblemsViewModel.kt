package com.example.contestifyme.features.problemsFeature.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contestifyme.features.problemsFeature.data.ProblemsRepository
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.StringBuilder

class ProblemsViewModel(
    private val problemsRepository: ProblemsRepository,

) : ViewModel() {
    var problemsUiState: StateFlow<ProblemState> = problemsRepository.getProblemsFromDb().map {
        ProblemState(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProblemState())

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
                            unique = "${item.contestId}/${item.index}",
                            index = item.index,
                            tags = getTags(item.tags),
                            name = item.name
                        )
                    )
                }
                println(list)
                problemsRepository.upsertProblems(list)
            } catch (e: HttpException) {
                Log.d("prafull", "getProblems: http")
            } catch (e: IOException) {
                Log.d("prafull", "getProblems: io")
            }
        }
    }
}
fun getTags(list: List<String>) : String {
    list.sorted()
    val sb = StringBuilder()
    for (i in list) sb.append(i).append(" ")
    return sb.trim().toString()
}
sealed interface ProblemsUiState {
    data class Loading(val data: ProblemsDto) : ProblemsUiState
    data class Success(val data: ProblemsDto) : ProblemsUiState
    object Error : ProblemsUiState
}
data class ProblemState(
    val entity: List<ProblemsEntity> = emptyList()
)