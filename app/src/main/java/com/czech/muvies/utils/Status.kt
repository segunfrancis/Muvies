package com.czech.muvies.utils

@Deprecated(
    message = "This class has been deprecated, replace with Result",
    replaceWith = ReplaceWith("Result", "com.czech.muvies.utils.Result"),
    level = DeprecationLevel.WARNING
)
enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}