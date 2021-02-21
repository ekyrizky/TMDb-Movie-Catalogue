package com.ekyrizky.moviecatalogue.api

import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.MoviesResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<MovieDetailResponse>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<TvShowResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Call<TvShowDetailResponse>
}