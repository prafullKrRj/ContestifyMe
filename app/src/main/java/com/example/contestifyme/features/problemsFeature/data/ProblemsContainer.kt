package com.example.contestifyme.features.problemsFeature.data

import android.content.Context

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