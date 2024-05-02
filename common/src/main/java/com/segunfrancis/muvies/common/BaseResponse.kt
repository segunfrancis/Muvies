package com.segunfrancis.muvies.common

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    val page: Int? = null,
    val results: T? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)
