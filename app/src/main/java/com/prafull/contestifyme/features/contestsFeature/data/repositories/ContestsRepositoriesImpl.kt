package com.prafull.contestifyme.features.contestsFeature.data.repositories

import android.content.Context
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.features.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.features.contestsFeature.data.remote.ContestsApiService
import com.prafull.contestifyme.features.contestsFeature.domain.model.ContestsDto
import com.prafull.contestifyme.features.contestsFeature.domain.repositories.ContestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ContestsRepositoryImpl @Inject constructor (
    private val context: Context,
    private val contestsDao: ContestsDao,
    private val contestsApi: ContestsApiService
) : ContestsRepository {
    override suspend fun insertContests(contestsEntity: ContestsEntity) {
        contestsDao.insertContests(contestsEntity)
    }

    override fun getContests(): Flow<List<ContestsEntity>> {
        return contestsDao.getContests()
    }

    override suspend fun getContestsFromApi(): ContestsDto {
        return contestsApi.getContestsList()
    }
}