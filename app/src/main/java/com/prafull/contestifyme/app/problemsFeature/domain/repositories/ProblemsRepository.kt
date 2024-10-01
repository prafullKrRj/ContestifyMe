package com.prafull.contestifyme.app.problemsFeature.domain.repositories

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.app.problemsFeature.domain.model.ProblemsDto
import com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru.AcmsguruDto
import kotlinx.coroutines.flow.Flow

interface ProblemsRepository {
    suspend fun getProblemsFromApi(): ProblemsDto

    fun getListOfProblems(): Flow<BaseClass<List<ProblemsEntity>>>
    fun getProblemsFromDb(): Flow<List<ProblemsEntity>>

    suspend fun deleteAll()
    suspend fun upsertProblems(problems: List<ProblemsEntity>)
    fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>>
    suspend fun getSortedByAsc(): List<ProblemsEntity>

    suspend fun getAcmsGuruProblems(): Flow<BaseClass<AcmsguruDto>>
}
