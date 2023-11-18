package com.example.contestifyme.features.problemsFeature.data

import com.example.contestifyme.features.problemsFeature.problemsConstants.Constants
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import com.example.contestifyme.features.problemsFeature.network.ProblemsApiService

interface ProblemsRepository {
    suspend fun getProblems(): ProblemsDto
}

class ProblemsRepositoryImpl (
    private val problemsApiService: ProblemsApiService
) : ProblemsRepository {
    override suspend fun getProblems(): ProblemsDto = problemsApiService.getProblems(Constants.getProblems())

}