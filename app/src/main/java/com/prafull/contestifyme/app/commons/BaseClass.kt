package com.prafull.contestifyme.app.commons

sealed class BaseClass<out T> {
    data class Success<out T>(val data: T) : BaseClass<T>()
    data class Error(val exception: Exception) : BaseClass<Nothing>()
    object Loading : BaseClass<Nothing>()
    object Initial : BaseClass<Nothing>()
}
