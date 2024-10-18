package com.prafull.contestifyme.app.contestsFeature.domain.repositories

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.contestsFeature.domain.model.particularContest.ParticularContestDto
import kotlinx.coroutines.flow.Flow

interface ContestsRepository {
    suspend fun insertContest(contestsEntity: ContestsEntity)
    suspend fun insertContests(contests: List<ContestsEntity>)
    fun getContests(): Flow<List<ContestsEntity>>
    suspend fun getContestsFromApi(): Flow<BaseClass<List<ContestsEntity>>>

    suspend fun getParticularContestDetails(contestId: String): Flow<BaseClass<ParticularContestDto>>
}
