package com.prafull.contestifyme.app.contestsFeature.domain.repositories

import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.contestsFeature.domain.model.ContestsDto
import kotlinx.coroutines.flow.Flow

interface ContestsRepository {
    suspend fun insertContests(contestsEntity: ContestsEntity)
    fun getContests(): Flow<List<ContestsEntity>>
    suspend fun getContestsFromApi(): ContestsDto
}
