package com.example.contestifyme.features.problemsFeature.data

import com.example.contestifyme.features.problemsFeature.data.local.ProblemsDao
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import com.example.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import kotlinx.coroutines.flow.Flow

interface ProblemsRepository {
    suspend fun getProblemsFromApi(): ProblemsDto

    fun getProblemsFromDb(): Flow<List<ProblemsEntity>>

    suspend fun deleteAll()
    suspend fun upsertProblems(problems: List<ProblemsEntity>)
}

class ProblemsRepositoryImpl (
    private val problemsApiService: ProblemsApiService,
    private val problemsDao: ProblemsDao
) : ProblemsRepository {
    override suspend fun getProblemsFromApi(): ProblemsDto = problemsApiService.getProblems(ProblemsConstants.getProblems())
    override fun getProblemsFromDb(): Flow<List<ProblemsEntity>> = problemsDao.getProblemsFromDb()

    override suspend fun deleteAll() = problemsDao.deleteALl()
    override suspend fun upsertProblems(problems: List<ProblemsEntity>) = problemsDao.upsertProblems(problems = problems)

}