package com.prafull.contestifyme.app.problemsFeature.domain.repositories

import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.app.problemsFeature.domain.model.ProblemsDto
import kotlinx.coroutines.flow.Flow

interface ProblemsRepository {
    suspend fun getProblemsFromApi(): ProblemsDto

    fun getProblemsFromDb(): Flow<List<ProblemsEntity>>

    suspend fun deleteAll()
    suspend fun upsertProblems(problems: List<ProblemsEntity>)
    fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>>
    suspend fun getSortedByAsc(): List<ProblemsEntity>
}
