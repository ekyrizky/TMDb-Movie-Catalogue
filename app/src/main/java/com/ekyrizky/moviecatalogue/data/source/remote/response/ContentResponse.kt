package com.ekyrizky.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ContentResponse<T>(
    @field:SerializedName("results")
    val results: List<T>?
)