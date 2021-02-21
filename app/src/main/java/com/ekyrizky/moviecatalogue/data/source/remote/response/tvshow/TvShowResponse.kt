package com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName

data class TvShowResponse(

        @SerializedName("results")
        val results: List<PopularTvShowsResponse>
)