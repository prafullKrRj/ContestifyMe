package com.example.contestifyme.features.profileFeature.model

data class UserSubmissions(
    val id: Int,
    val name: String,
    val verdict: String,
    val time: Int,
    val contestId: Int,
    val index: String,
    val rating: Int,
)