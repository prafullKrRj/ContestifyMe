package com.example.contestifyme.features.compareFeature.data.source

import android.content.Context

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