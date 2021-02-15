package com.ekyrizky.moviecatalogue.data.source.remote

import android.util.Log
import com.ekyrizky.moviecatalogue.api.ApiConfig
import com.ekyrizky.moviecatalogue.data.source.remote.response.ContentResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import com.ekyrizky.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource()
        }
    }

     fun getNowPlayingMovies(callback: LoadNowPlayingMoviesCallback) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getNowPlayingMovies().enqueue(object : Callback<ContentResponse<MovieResponse>> {
            override fun onResponse(
                call: Call<ContentResponse<MovieResponse>>,
                response: Response<ContentResponse<MovieResponse>>
            ) {
                response.body()?.results?.let { callback.onNowPlayingMoviesReceived(it) }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ContentResponse<MovieResponse>>, t: Throwable) {
                Log.d("Error : ", t.message.toString())
            }
        })
    }

    fun getMovieDetail(movieId: Int, callback: LoadMovieDetailCallback) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getMovieDetail(movieId).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.let { callback.onMovieDetailReceived(it) }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("Error : ", t.message.toString())
            }
        })
    }

     fun getOnTheAirTvShows(callback: LoadOnTheAirTvShowsCallback) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getOnTheAirTvShows().enqueue(object : Callback<ContentResponse<TvShowResponse>> {
            override fun onResponse(
                call: Call<ContentResponse<TvShowResponse>>,
                response: Response<ContentResponse<TvShowResponse>>
            ) {
                response.body()?.results?.let { callback.onOnAirTvShowsReceived(it) }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ContentResponse<TvShowResponse>>, t: Throwable) {
                Log.d("Error : ", t.message.toString())
            }
        })
    }

     fun getTvShowDetail(tvShowId: Int, callback: LoadTvShowDetailCallback) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getTvShowDetail(tvShowId).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                response.body()?.let { callback.onTvShowDetailReceived(it) }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d("Error : ", t.message.toString())
            }

        })
    }

    interface LoadNowPlayingMoviesCallback {
        fun onNowPlayingMoviesReceived(movieResponse: List<MovieResponse>)
    }

    interface LoadMovieDetailCallback {
        fun onMovieDetailReceived(movieResponse: MovieResponse)
    }

    interface LoadOnTheAirTvShowsCallback {
        fun onOnAirTvShowsReceived(tvShowResponse: List<TvShowResponse>)
    }

    interface LoadTvShowDetailCallback {
        fun onTvShowDetailReceived(tvShowResponse: TvShowResponse)
    }
}