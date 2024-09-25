package com.prafull.contestifyme.app.problemsFeature.data.repositories

import com.prafull.contestifyme.app.problemsFeature.constants.ProblemsConstants
import com.prafull.contestifyme.app.problemsFeature.data.local.ProblemsDao
import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.app.problemsFeature.data.remote.ProblemsApiService
import com.prafull.contestifyme.app.problemsFeature.domain.model.ProblemsDto
import com.prafull.contestifyme.app.problemsFeature.domain.repositories.ProblemsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ProblemsRepositoryImpl() : ProblemsRepository, KoinComponent {
    private val problemsApiService: ProblemsApiService by inject()
    private val dao: ProblemsDao by inject()
    override suspend fun getProblemsFromApi(): ProblemsDto = problemsApiService.getProblems(
        ProblemsConstants.getProblemsByTags(
            emptyList()
        )
    )

    override fun getProblemsFromDb(): Flow<List<ProblemsEntity>> = dao.getProblemsFromDb()

    override suspend fun deleteAll() = dao.deleteALl()
    override suspend fun upsertProblems(problems: List<ProblemsEntity>) =
        dao.upsertProblems(problems = problems)

    override fun getSortedByDesc(rating: Int): Flow<List<ProblemsEntity>> =
        dao.getProblemsByRatingDesc(rating)

    override suspend fun getSortedByAsc(): List<ProblemsEntity> = dao.getProblemsByRatingAsc()

}