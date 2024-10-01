package com.prafull.contestifyme.app.friendsFeature.data.repo

import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.app.friendsFeature.domain.FriendRepo
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity
import com.prafull.contestifyme.network.CodeForcesApi
import com.prafull.contestifyme.network.CodeforcesUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FriendRepoImpl : FriendRepo, KoinComponent {
    private val api by inject<CodeForcesApi>()
    private val dao by inject<FriendsDao>()
    override suspend fun getFriendsDataFromDB(): List<UserInfoEntity> {
        return dao.getFriendsData()
    }

    override fun getSingleFriendData(handle: String): Flow<BaseClass<UserData>> {
        return flow {
            try {
                val friendInfo = api.getUserInfo(CodeforcesUtil.getUserInfoUrl(handle))
                val ratings = api.getUserRating(CodeforcesUtil.getUserRatingUrl(handle))
                val status = api.getStatus(CodeforcesUtil.getUserSubmissionsUrl(handle))
                emit(
                    BaseClass.Success(
                        UserData(
                            handle = handle,
                            usersInfo = friendInfo.result[0],
                            userRating = ratings.toUserRatings(),
                            userSubmissions = status.toUserStatus()
                        )
                    )
                )
            } catch (e: Exception) {
                emit(BaseClass.Error(e))
            }
        }
    }

    override fun updateFriends(): Flow<BaseClass<List<UserInfoEntity>>> {
        return flow {
            try {
                val friends = dao.getFriendsData().map { it.handle }
                if (friends.isNullOrEmpty()) {
                    return@flow
                }
                val results = api.getUserInfo(CodeforcesUtil.getUserInfoUrl(friends))
                val response = results.result.map { it.toUserInfoEntity() }
                dao.insertUsersInfo(response)
                emit(BaseClass.Success(response))
            } catch (e: Exception) {
                emit(BaseClass.Error(e))
            }
        }
    }

    override fun insertFriend(handle: String): Flow<BaseClass<List<UserInfoEntity>>> {
        return flow {
            try {
                val user = api.getUserInfo(CodeforcesUtil.getUserInfoUrl(handle))
                val userInfoEntity = user.result[0].toUserInfoEntity()
                dao.insertUserInfo(userInfoEntity)
                emit(BaseClass.Success(dao.getFriendsData()))
            } catch (e: Exception) {
                emit(BaseClass.Error(e))
            }
        }
    }

    override suspend fun getFriendDataFromDb(handle: String): UserInfoEntity? {
        return dao.getUserInfo(handle)
    }
}