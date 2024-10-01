package com.prafull.contestifyme.app.profileFeature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserRatingEntity
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserSubmissionsEntity

@Dao
interface ProfileDao {

    @Query("SELECT * FROM user_info")
    suspend fun getUserInfo(): UserInfoEntity

    @Query("SELECT * FROM user_rating")
    suspend fun getUserRating(): List<UserRatingEntity>

    @Query("SELECT * FROM user_submissions order by time desc")
    suspend fun getUserSubmissions(): List<UserSubmissionsEntity>

    suspend fun getUserData(handle: String): UserData {
        return UserData(
            handle = handle,
            usersInfo = getUserInfo().toUserResult(),
            userRating = getUserRating().map { it.toUserRating() } ?: emptyList(),
            userSubmissions = getUserSubmissions().map { it.toUserSubmissions() } ?: emptyList()
        )
    }

    @Insert
    suspend fun insertUserInfo(userInfoEntity: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRating(userRatingEntity: List<UserRatingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSubmissions(userSubmissionsEntity: List<UserSubmissionsEntity>)

    suspend fun insertUserData(userData: UserData) {
        insertUserInfo(userData.usersInfo.toUserInfoEntity())
        insertUserRating(userData.toUserRatingEntities())
        insertUserSubmissions(userData.toUserSubmissionEntities())
    }
}