package com.androiddevs.mvvmnewsapp.utils

sealed class Resource<T>( // Helpful to work with success, error and loading states
    val data: T? = null,
    val message: String? = null,

) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(error: String, data: T? = null): Resource<T>(message = error, data = data)
    class Loading<T>: Resource<T>()
}