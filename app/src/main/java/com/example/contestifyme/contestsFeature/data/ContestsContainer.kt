package com.example.contestifyme.contestsFeature.data

import android.content.Context
import com.example.contestifyme.contestsFeature.model.ContestsRepository
import com.example.contestifyme.contestsFeature.model.ContestsRepositoryImpl

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