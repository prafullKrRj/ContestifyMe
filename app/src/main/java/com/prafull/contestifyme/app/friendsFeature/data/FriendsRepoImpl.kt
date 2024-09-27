package com.prafull.contestifyme.app.friendsFeature.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import co.yml.charts.common.extensions.isNotNull
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendEntity
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.app.friendsFeature.data.network.FriendApiService
import com.prafull.contestifyme.app.friendsFeature.domain.FriendData
import com.prafull.contestifyme.app.friendsFeature.domain.FriendsRepo
import com.prafull.contestifyme.app.profileFeature.constants.ProfileConstants
import com.prafull.contestifyme.onboard.model.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okio.IOException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class FriendsRepoImpl(
    private val context: Context
) : FriendsRepo, KoinComponent {
    private val dao: FriendsDao by inject()
    private val apiService: FriendApiService by inject()


    override fun getFriends(): Flow<BaseClass<List<UserResult>>> = flow {
        try {
            val friends = dao.getFriendsList()
            if (friends.isEmpty()) {
                emit(BaseClass.Success(emptyList()))
                return@flow
            }
            val handles = friends.map {
                it.handle!!
            }
            val response = apiService.getFriendsData(getUrl(handles))
            emit(BaseClass.Success(response.result.map { it.toUserResult() }))
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }

            emit(BaseClass.Success(dao.getFriendsList()))
        } catch (e: HttpException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error try refreshing", Toast.LENGTH_SHORT).show()
            }
            emit(BaseClass.Success(dao.getFriendsList()))
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error try refreshing", Toast.LENGTH_SHORT).show()
            }
            emit(BaseClass.Success(dao.getFriendsList()))
        }
    }

    override fun getFriendData(handle: String): Flow<BaseClass<FriendData>> = flow {
        try {
            val friendInfo =
                apiService.getFriendInfo(getUrl(listOf(handle))).result[0].toUserResult()

            val submissionInfo =
                apiService.getFriendSubmissions(ProfileConstants.getUserStatus(handle)).submissions.map { it.toUserSubmission() }
            val ratingInfo =
                apiService.getFriendRating(ProfileConstants.getUserRating(handle)).result.map { it.toUserRating() }

            dao.insertFriend(
                FriendEntity(
                    handle = handle.lowercase(),
                    rating = friendInfo.rating!!,
                    friendInfo = friendInfo,
                    friendSubmissions = submissionInfo,
                    friendRating = ratingInfo
                )
            )
            emit(BaseClass.Success(dao.getFriendData(handle.lowercase()).toFriendData()))
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error try refreshing", Toast.LENGTH_SHORT).show()
            }
            val friendData = dao.getFriendData(handle.lowercase())
            if (friendData.isNotNull()) {
                emit(BaseClass.Success(friendData.toFriendData()))
            } else {
                emit(BaseClass.Error(e))
            }
        }
    }

    override fun addUpdateFriend(friendData: FriendData): Flow<BaseClass<Unit>> {
        return callbackFlow {
            awaitClose { }
        }
    }

    override suspend fun addFriend(handle: String): Flow<BaseClass<List<UserResult>>> = flow {
        try {
            val response = apiService.getFriendsData(getUrl(listOf(handle)))
            if (response.status == "FAILED") {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User not found or server error", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Log.d("FriendRepo", response.result[0].toString())
                dao.insertFriend(
                    FriendEntity(
                        handle = handle.lowercase(),
                        rating = response.result[0].rating!!,
                        friendInfo = response.result[0].toUserResult(),
                    )
                )
            }
            emit(BaseClass.Success(dao.getFriendsList()))
        } catch (e: IOException) {
            Log.d("FriendRepo", e.toString())
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
            emit(BaseClass.Error(e))
        } catch (e: HttpException) {
            withContext(Dispatchers.Main) {
                Log.d("FriendRepo", e.toString())
                Toast.makeText(context, "Error try refreshing $e", Toast.LENGTH_SHORT).show()
            }
            emit(BaseClass.Error(e))
        } catch (e: Exception) {
            Log.d("FriendRepo", e.toString())
            emit(BaseClass.Error(e))
        }
    }

    private fun getUrl(handles: List<String>): String {
        return "https://codeforces.com/api/user.info?handles=${handles.joinToString(";")}"
    }

}