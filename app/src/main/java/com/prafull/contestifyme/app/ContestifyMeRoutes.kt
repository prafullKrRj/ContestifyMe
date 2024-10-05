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
    data object AI : App


    @Serializable
    data class WebViewScreen(val url: String, val heading: String) : App

    @Serializable
    data object SubmissionScreen : App

    @Serializable
    data object Settings : App

    @Serializable
    data object LibrariesList : App
}

sealed interface AiRoutes {

    @Serializable
    data object ChatScreen : AiRoutes

    @Serializable
    data object EnrollAi : AiRoutes

    @Serializable
    data object ApiSettings : AiRoutes

}

sealed interface ContestRoutes {
    @Serializable
    data class ContestScreen(val contestId: String, val contestName: String) : ContestRoutes

    @Serializable
    data object ContestList : ContestRoutes
}