package com.czech.muvies.utils

@Deprecated(
    message = "This class has been deprecated, replace with Result",
    replaceWith = ReplaceWith("Result", "com.czech.muvies.utils.Result"),
    level = DeprecationLevel.WARNING
)
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    class Error(val error: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
