package com.example.ks.utils

/**
 * Created by skycap.
 */
sealed class ResultWrapper<out T>{
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): ResultWrapper<Nothing>()
    object SocketTimeOutError: ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}
data class ErrorResponse(
    val error_des : String?,
    val causes: Map<String, String> = emptyMap()
)