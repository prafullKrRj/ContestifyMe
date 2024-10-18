package com.prafull.contestifyme.app.contestsFeature.data.repositories

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.contestsFeature.data.remote.ContestsApiService
import com.prafull.contestifyme.app.contestsFeature.domain.model.ContestsDto
import com.prafull.contestifyme.app.contestsFeature.domain.model.particularContest.ParticularContestDto
import com.prafull.contestifyme.app.contestsFeature.domain.repositories.ContestsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ContestsRepositoryImpl() : ContestsRepository, KoinComponent {

    private val contestsApi: ContestsApiService by inject()
    private val contestsDao: ContestsDao by inject()
    override suspend fun insertContest(contestsEntity: ContestsEntity) {
        contestsDao.insertContests(contestsEntity)
    }

    override suspend fun insertContests(contests: List<ContestsEntity>) {
        contestsDao.insertContests(contests)
    }

    override fun getContests(): Flow<List<ContestsEntity>> {
        return contestsDao.getContests()
    }

    override suspend fun getContestsFromApi(): Flow<BaseClass<List<ContestsEntity>>> = flow {
        emit(BaseClass.Loading)
        try {
            val response: ContestsDto = contestsApi.getContestsList()
            if (response.status == "FAILED") {
                emit(BaseClass.Error(Exception("Failed to fetch data")))
            } else {
                val list = response.toContestEntities()
                emit(BaseClass.Success(list))
            }
        } catch (e: Exception) {
            emit(BaseClass.Error(e))
        }
    }

    override suspend fun getParticularContestDetails(contestId: String): Flow<BaseClass<ParticularContestDto>> =
        flow {
            try {
                val particularContestDetails = contestsApi.getContestDetails(getUrl(contestId))
                if (particularContestDetails.status == "FAILED") {
                    emit(BaseClass.Error(Exception("Failed to fetch data")))
                } else {
                    emit(BaseClass.Success(particularContestDetails))
                }
            } catch (e: Exception) {
                emit(BaseClass.Error(e))
            }
        }

    private fun getUrl(contestId: String): String =
        "https://codeforces.com/api/contest.standings?contestId=$contestId&from=1&count=1&showUnofficial=true"
}
