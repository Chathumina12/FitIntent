package com.campus.fitintent.utils

/**
 * Generic result wrapper for handling success and error states
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val exception: Exception? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    val isLoading: Boolean
        get() = this is Loading

    fun <R> map(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            is Loading -> this
        }
    }

    fun <R> flatMap(transform: (T) -> Result<R>): Result<R> {
        return when (this) {
            is Success -> transform(data)
            is Error -> this
            is Loading -> this
        }
    }

    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (String) -> Unit): Result<T> {
        if (this is Error) action(message)
        return this
    }

    inline fun onLoading(action: () -> Unit): Result<T> {
        if (this is Loading) action()
        return this
    }

    fun getOrNull(): T? {
        return if (this is Success) data else null
    }

    fun getOrDefault(default: T): T {
        return if (this is Success) data else default
    }

    fun getOrThrow(): T {
        return when (this) {
            is Success -> data
            is Error -> throw exception ?: Exception(message)
            is Loading -> throw IllegalStateException("Result is still loading")
        }
    }
}

/**
 * Extension function to handle Result in coroutines
 */
suspend fun <T> Result<T>.suspendOnSuccess(action: suspend (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

suspend fun <T> Result<T>.suspendOnError(action: suspend (String) -> Unit): Result<T> {
    if (this is Result.Error) action(message)
    return this
}