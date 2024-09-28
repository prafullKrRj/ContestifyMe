package com.prafull.contestifyme.app.contestsFeature.domain.repositories

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.contestsFeature.domain.model.ContestsDto
import com.prafull.contestifyme.app.contestsFeature.domain.model.particularContest.ParticularContestDto
import kotlinx.coroutines.flow.Flow

interface ContestsRepository {
    suspend fun insertContests(contestsEntity: ContestsEntity)
    fun getContests(): Flow<List<ContestsEntity>>
    suspend fun getContestsFromApi(): ContestsDto


    suspend fun getParticularContestDetails(contestId: String): Flow<BaseClass<ParticularContestDto>>
}
