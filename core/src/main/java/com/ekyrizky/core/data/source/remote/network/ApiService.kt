package com.ekyrizky.core.data.source.remote.network

import com.ekyrizky.core.BuildConfig
import com.ekyrizky.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.core.data.source.remote.response.movie.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
            @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : MovieDetailResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        ): MoviesResponse
}