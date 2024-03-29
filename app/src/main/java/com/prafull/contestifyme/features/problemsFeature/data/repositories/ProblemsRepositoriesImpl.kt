package com.prafull.contestifyme.features.problemsFeature.data.repositories

import com.prafull.contestifyme.features.problemsFeature.data.local.ProblemsDao
import com.prafull.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import com.prafull.contestifyme.features.problemsFeature.domain.model.ProblemsDto
import com.prafull.contestifyme.features.problemsFeature.domain.repositories.ProblemsRepository
import com.prafull.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProblemsRepositoryImpl @Inject constructor(
    private val problemsApiService: ProblemsApiService,
    private val problemsDao: ProblemsDao
) : ProblemsRepository {
    override suspend fun getProblemsFromApi(): ProblemsDto = problemsApiService.getProblems(
        ProblemsConstants.getProblemsByTags(
        emptyList()
    ))
    override fun getProblemsFromDb(): Flow<List<ProblemsEntity>> = problemsDao.getProblemsFromDb()

    override suspend fun deleteAll() = problemsDao.deleteALl()
    override suspend fun upsertProblems(problems: List<ProblemsEntity>) = problemsDao.upsertProblems(problems = problems)
    override fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>> = problemsDao.getProblemsByRatingDesc(rating)
    override suspend fun getSortedByAsc(): List<ProblemsEntity> = problemsDao.getProblemsByRatingAsc()

}