package com.example.contestifyme.compareFeature.data

import android.content.Context
import com.example.contestifyme.compareFeature.model.CompareRepository
import com.example.contestifyme.compareFeature.model.CompareRepositoryImpl

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