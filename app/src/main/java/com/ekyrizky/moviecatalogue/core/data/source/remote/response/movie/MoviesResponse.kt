package com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

        @SerializedName("results")
        val results: List<PopularMoviesResponse>
)