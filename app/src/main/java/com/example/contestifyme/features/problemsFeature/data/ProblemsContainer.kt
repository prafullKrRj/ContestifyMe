package com.example.contestifyme.features.problemsFeature.data

import android.content.Context
import com.example.contestifyme.features.problemsFeature.model.ProblemsRepository
import com.example.contestifyme.features.problemsFeature.model.ProblemsRepositoryImpl

interface ProblemsContainer {
    val problemsRepository: ProblemsRepository
}
class ProblemsContainerImpl(
    private val context: Context
) : ProblemsContainer {
    override val problemsRepository: ProblemsRepository by lazy {
        ProblemsRepositoryImpl()
    }

}