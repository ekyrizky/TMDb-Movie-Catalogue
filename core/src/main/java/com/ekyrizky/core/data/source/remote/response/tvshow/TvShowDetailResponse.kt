package com.ekyrizky.core.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("original_name")
    val originalName: String?,

    @field:SerializedName("tagline")
    val tagline: String?,

    @field:SerializedName("first_air_date")
    val firstAirDate: String?,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>?,

    @field:SerializedName("vote_average")
    val voteAverage: Double?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,
)