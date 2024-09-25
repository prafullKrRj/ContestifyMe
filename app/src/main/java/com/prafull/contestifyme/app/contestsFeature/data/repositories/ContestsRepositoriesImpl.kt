package com.prafull.contestifyme.app.contestsFeature.data.repositories

import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsDao
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity
import com.prafull.contestifyme.app.contestsFeature.data.remote.ContestsApiService
import com.prafull.contestifyme.app.contestsFeature.domain.model.ContestsDto
import com.prafull.contestifyme.app.contestsFeature.domain.repositories.ContestsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ContestsRepositoryImpl() : ContestsRepository, KoinComponent {

    private val contestsApi: ContestsApiService by inject()
    private val contestsDao: ContestsDao by inject()
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