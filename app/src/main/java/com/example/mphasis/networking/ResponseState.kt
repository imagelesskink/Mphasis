package com.example.mphasis.networking

sealed interface ResponseState {
    object Loading: ResponseState
    class Error(val msg: Throwable): ResponseState
    class Success<T>(val response: T): ResponseState
}
