package com.prafull.contestifyme.features.friendsFeature.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.prafull.contestifyme.commons.Resource
import com.prafull.contestifyme.features.friendsFeature.FriendsConstants
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDao
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.prafull.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.prafull.contestifyme.features.friendsFeature.domain.model.FriendsInfoResultDto
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FriendsRepositoryImpl() : FriendsRepository, KoinComponent {
    private val friendsDao: FriendsDao by inject()
    private val friendsApiService: FriendsApiService by inject()
    private val context: Context by inject()

    override suspend fun getFromInternet(): Flow<Resource<List<FriendsInfoResultDto>>> = flow {
        emit(Resource.Loading)
        try {
            friendsDao.getFriendsData().map {
                it.map { friendsDataEntity ->
                    friendsDataEntity.handle
                }
            }.collect { handles ->
                if (handles.isEmpty()) {
                    emit(Resource.Initial)
                    return@collect
                }
                val response = friendsApiService.getFriendsData(
                    FriendsConstants.getUserInfo(handles)
                )
                if (response.status == "OK" || response.status == "ok") {
                    val friendsDataEntity = response.result.map {
                        it.toFriendsDataEntity()
                    }
                    insertIntoDatabase(friendsDataEntity)
                    emit(Resource.Success(response.result))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
            Toast.makeText(context, "Error Loading New Data: ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override suspend fun getSingleFriendFromInternet(handle: String): Flow<Resource<FriendsInfoResultDto>> =
        flow {
            emit(Resource.Loading)
            try {
                val response = friendsApiService.getFriendsData(
                    FriendsConstants.getUserHandelInfo(handle)
                )
                if (response.status == "OK" || response.status == "ok") {
                    val friendsDataEntity = response.result.map {
                        it.toFriendsDataEntity()
                    }
                    insertIntoDatabase(friendsDataEntity)
                    emit(Resource.Success(response.result.first()))
                } else {
                    emit(Resource.Error(Exception("Error Loading New Data: ${response.status}")))
                    Toast.makeText(
                        context,
                        "Error Loading New Data: ${response.status}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.d("img", "getSingleFriendFromInternet: ${e.message}")
                emit(Resource.Error(e))
                Toast.makeText(context, "Error Loading New Data: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override suspend fun insertIntoDatabase(data: List<FriendsDataEntity>) {
        friendsDao.insertFriendsData(data)
    }

    override fun getFromDatabase(): Flow<List<FriendsDataEntity>> {
        return friendsDao.getFriendsData()
    }
}