package com.example.contestifyme.features.compareFeature.data

import android.content.Context
import com.example.contestifyme.features.compareFeature.model.CompareRepository
import com.example.contestifyme.features.compareFeature.model.CompareRepositoryImpl

interface CompareContainer {
    val compareRepository: CompareRepository
}
class CompareContainerImpl(
    private val context: Context
) : CompareContainer {
    override val compareRepository: CompareRepository by lazy {
        CompareRepositoryImpl()
    }
}