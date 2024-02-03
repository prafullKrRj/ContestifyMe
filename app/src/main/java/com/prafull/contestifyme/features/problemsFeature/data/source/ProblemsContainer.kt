package com.prafull.contestifyme.features.problemsFeature.data.source

import android.content.Context
import com.prafull.contestifyme.constants.Constants.BASE_URL
import com.prafull.contestifyme.features.problemsFeature.data.local.ProblemsDatabase
import com.prafull.contestifyme.features.problemsFeature.data.remote.ProblemsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ProblemsContainer {
    val problemsRepository: ProblemsRepository
}
class ProblemsContainerImpl(
    private val context: Context
) : ProblemsContainer {


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ProblemsApiService = retrofit.create(ProblemsApiService::class.java)
    override val problemsRepository: ProblemsRepository by lazy {
        ProblemsRepositoryImpl(apiService, ProblemsDatabase.getDatabase(context).problemsDao())
    }
}