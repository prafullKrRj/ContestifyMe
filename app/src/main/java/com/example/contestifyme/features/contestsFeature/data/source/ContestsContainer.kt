package com.example.contestifyme.features.contestsFeature.data.source

import android.content.Context
import com.example.contestifyme.constants.Constants.BASE_URL
import com.example.contestifyme.features.contestsFeature.ContestsRepository
import com.example.contestifyme.features.contestsFeature.ContestsRepositoryImpl
import com.example.contestifyme.features.contestsFeature.data.local.ContestDB
import com.example.contestifyme.features.contestsFeature.data.remote.ContestsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ContestsContainer {
    val contestsRepository: ContestsRepository
}
class ContestsContainerImpl(
    private val context: Context
) : ContestsContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val contestsApi = retrofit.create(ContestsApiService::class.java)
    override val contestsRepository: ContestsRepository by lazy {
        ContestsRepositoryImpl(context, ContestDB.getInstance(context).contestsDao(), contestsApi)
    }

}