package com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class PopularTvShowsResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("original_name")
	val originalName: String? = null,

	@field:SerializedName("first_air_date")
	val firstAirDate: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,
)
