package com.ekyrizky.moviecatalogue.api

import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.data.source.remote.response.ContentResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<ContentResponse<MovieResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<MovieResponse>

    @GET("tv/on_the_air")
    fun getOnTheAirTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<ContentResponse<TvShowResponse>>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<TvShowResponse>
}