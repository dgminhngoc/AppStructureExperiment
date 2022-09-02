package com.example.myapplication.data

sealed class RequestResult<T> {
    data class OnSuccess<T>(val data: T?) : RequestResult<T>()
    data class OnError<T>(
        val errorCode: Int? = null,
        val exception: Exception? = null
    ): RequestResult<T>()
}