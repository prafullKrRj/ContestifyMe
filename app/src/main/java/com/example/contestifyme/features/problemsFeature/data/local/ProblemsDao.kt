package com.example.contestifyme.features.problemsFeature.data.local

import android.media.Rating
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemsDao {
    @Upsert(entity = ProblemsEntity::class)
    suspend fun upsertProblems(problems: List<ProblemsEntity>)

    @Query("SELECT * FROM problems_entity")
    fun getProblemsFromDb() : Flow<List<ProblemsEntity>>

    @Query("DELETE FROM problems_entity")
    suspend fun deleteALl()

    @Query("SELECT * FROM problems_entity WHERE rating = :rating")
    fun getProblemsByRatingDesc(rating: Int) : Flow<List<ProblemsEntity>>
    @Query("SELECT * FROM problems_entity ORDER BY rating ASC")
    fun getProblemsByRatingAsc() : List<ProblemsEntity>
}