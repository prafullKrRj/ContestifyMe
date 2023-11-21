package com.example.contestifyme.features.problemsFeature.data

import com.example.contestifyme.features.problemsFeature.data.local.ProblemsDao
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import com.example.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import kotlinx.coroutines.flow.Flow

interface ProblemsRepository {
    suspend fun getProblemsFromApi(url: String): ProblemsDto

    fun getProblemsFromDb(): Flow<List<ProblemsEntity>>

    suspend fun deleteAll()
    suspend fun upsertProblems(problems: List<ProblemsEntity>)
    fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>>
    fun getSortedByAsc(): Flow<List<ProblemsEntity>>
}

class ProblemsRepositoryImpl (
    private val problemsApiService: ProblemsApiService,
    private val problemsDao: ProblemsDao
) : ProblemsRepository {
    override suspend fun getProblemsFromApi(url: String): ProblemsDto = problemsApiService.getProblems(url)
    override fun getProblemsFromDb(): Flow<List<ProblemsEntity>> = problemsDao.getProblemsFromDb()

    override suspend fun deleteAll() = problemsDao.deleteALl()
    override suspend fun upsertProblems(problems: List<ProblemsEntity>) = problemsDao.upsertProblems(problems = problems)
    override fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>> = problemsDao.getProblemsByRatingDesc(rating)
    override fun getSortedByAsc(): Flow<List<ProblemsEntity>> = problemsDao.getProblemsByRatingAsc()

}