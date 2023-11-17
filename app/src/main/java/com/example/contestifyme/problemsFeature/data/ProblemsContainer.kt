package com.example.contestifyme.problemsFeature.data

import android.content.Context
import com.example.contestifyme.problemsFeature.model.ProblemsRepository

interface ProblemsContainer {
    val problemsRepository: ProblemsRepository
}
class ProblemsContainerImpl(
    private val context: Context
) : ProblemsContainer {
    override val problemsRepository: ProblemsRepository
        get() = TODO("Not yet implemented")

}