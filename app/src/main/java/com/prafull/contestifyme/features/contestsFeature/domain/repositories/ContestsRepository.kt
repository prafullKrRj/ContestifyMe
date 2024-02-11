package com.prafull.contestifyme.features.contestsFeature.domain.repositories

import android.content.Context
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.features.contestsFeature.data.remote.ContestsApiService
import com.prafull.contestifyme.features.contestsFeature.domain.model.ContestsDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ContestsRepository {
    suspend fun insertContests(contestsEntity: ContestsEntity)
    fun getContests(): Flow<List<ContestsEntity>>
    suspend fun getContestsFromApi(): ContestsDto
}
