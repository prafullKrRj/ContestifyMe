package com.example.contestifyme.startOnBoard.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: AppUser)

    @Query("SELECT * FROM AppUser WHERE id = 1")
    fun getUser(): Flow<List<AppUser>>
}