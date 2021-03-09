package com.ekyrizky.core.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResultResponse(

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("original_title")
	val originalTitle: String?,

	@field:SerializedName("release_date")
	val releaseDate: String?,

	@field:SerializedName("vote_average")
	val voteAverage: Double?,

	@field:SerializedName("overview")
	val overview: String?,

	@field:SerializedName("poster_path")
	val posterPath: String?,

	@field:SerializedName("backdrop_path")
	val backdropPath: String?,
)
