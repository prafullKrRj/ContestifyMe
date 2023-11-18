package com.example.contestifyme.features.contestsFeature.data

import android.content.Context
import com.example.contestifyme.features.contestsFeature.model.ContestsRepository
import com.example.contestifyme.features.contestsFeature.model.ContestsRepositoryImpl

interface ContestsContainer {
    val contestsRepository: ContestsRepository
}
class ContestsContainerImpl(
    private val context: Context
) : ContestsContainer {
    override val contestsRepository: ContestsRepository by lazy {
        ContestsRepositoryImpl()
    }

}