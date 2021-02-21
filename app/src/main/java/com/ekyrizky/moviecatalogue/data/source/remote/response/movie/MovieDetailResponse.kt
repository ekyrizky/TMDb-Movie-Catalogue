package com.ekyrizky.moviecatalogue.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("original_title")
    val originalTitle: String,

    @field:SerializedName("tagline")
    val tagline: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("runtime")
    val runtime: Int,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String
)