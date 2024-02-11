package com.prafull.contestifyme.features.friendsFeature.data.source

import android.content.Context
import com.prafull.contestifyme.features.friendsFeature.data.remote.FriendsApiService
import com.prafull.contestifyme.features.friendsFeature.domain.repositories.FriendsRepository
import javax.inject.Inject


class FriendsRepositoryImpl @Inject constructor (
    private val friendsApiService: FriendsApiService,
    private val context: Context
) : FriendsRepository {

}