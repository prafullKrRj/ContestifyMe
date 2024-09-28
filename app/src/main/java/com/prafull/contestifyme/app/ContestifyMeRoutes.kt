package com.prafull.contestifyme.app

import kotlinx.serialization.Serializable

sealed interface App {


    @Serializable
    data object Profile : App

    @Serializable
    data object Friends : App

    @Serializable
    data object Contests : App

    @Serializable
    data object Problems : App

    @Serializable
    data class WebViewScreen(val url: String, val heading: String) : App

    @Serializable
    data class SubmissionScreen(val submissions: String) : App
}

sealed interface ContestRoutes {
    @Serializable
    data class ContestScreen(val contestId: String, val contestName: String) : ContestRoutes

    @Serializable
    data object ContestList : ContestRoutes
}