package com.example.contestifyme.features.problemsFeature.data.source

import com.example.contestifyme.features.problemsFeature.data.local.ProblemsDao
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import com.example.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import com.example.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants
import kotlinx.coroutines.flow.Flow

interface ProblemsRepository {
    suspend fun getProblemsFromApi(): ProblemsDto

    fun getProblemsFromDb(): Flow<List<ProblemsEntity>>

    suspend fun deleteAll()
    suspend fun upsertProblems(problems: List<ProblemsEntity>)
    fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>>
    suspend fun getSortedByAsc(): List<ProblemsEntity>
}

class ProblemsRepositoryImpl (
    private val problemsApiService: ProblemsApiService,
    private val problemsDao: ProblemsDao
) : ProblemsRepository {
    override suspend fun getProblemsFromApi(): ProblemsDto = problemsApiService.getProblems(ProblemsConstants.getProblemsByTags(
        emptyList()
    ))
    override fun getProblemsFromDb(): Flow<List<ProblemsEntity>> = problemsDao.getProblemsFromDb()

    override suspend fun deleteAll() = problemsDao.deleteALl()
    override suspend fun upsertProblems(problems: List<ProblemsEntity>) = problemsDao.upsertProblems(problems = problems)
    override fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>> = problemsDao.getProblemsByRatingDesc(rating)
    override suspend fun getSortedByAsc(): List<ProblemsEntity> = problemsDao.getProblemsByRatingAsc()

}