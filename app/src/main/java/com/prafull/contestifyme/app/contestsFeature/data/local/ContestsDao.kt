package com.prafull.contestifyme.app.contestsFeature.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContestsDao {

    @Upsert
    suspend fun insertContests(contestsEntity: ContestsEntity)

    @Query("SELECT * FROM contests")
    fun getContests(): Flow<List<ContestsEntity>>
}