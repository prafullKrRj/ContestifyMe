package com.prafull.contestifyme

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
}