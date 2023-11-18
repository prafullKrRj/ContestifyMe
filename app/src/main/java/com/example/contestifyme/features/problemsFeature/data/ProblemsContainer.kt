package com.example.contestifyme.features.problemsFeature.data

import android.content.Context
import com.example.contestifyme.constants.Constants.BASE_URL
import com.example.contestifyme.features.problemsFeature.network.ProblemsApiService
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
        ProblemsRepositoryImpl(apiService)
    }
}