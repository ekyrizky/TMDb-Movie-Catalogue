package com.ekyrizky.core.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowResultResponse(

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("original_name")
	val originalName: String?,

	@field:SerializedName("first_air_date")
	val firstAirDate: String?,

	@field:SerializedName("vote_average")
	val voteAverage: Double?,

	@field:SerializedName("overview")
	val overview: String?,

	@field:SerializedName("poster_path")
	val posterPath: String?,

	@field:SerializedName("backdrop_path")
	val backdropPath: String?,
)
