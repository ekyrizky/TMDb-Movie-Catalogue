package com.ekyrizky.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ekyrizky.moviecatalogue.api.ApiConfig
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.MoviesResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.TvShowResponse
import com.ekyrizky.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RemoteDataSource()
        }
    }

    fun getMovies(): LiveData<ApiResponse<List<PopularMoviesResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<PopularMoviesResponse>>>()

        ApiConfig.getApiService().getPopularMovies().enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
            ) {
                resultMovies.value = ApiResponse.success(response.body()?.results as List<PopularMoviesResponse> )
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.d("RemoteDataSource", "onFailure: ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovies
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieDetailResponse>>()

        ApiConfig.getApiService().getMovieDetail(movieId).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                resultMovie.value = ApiResponse.success(response.body() as MovieDetailResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.d("RemoteDataSource ", "onFailure: ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovie
    }

    fun getTvShows(): LiveData<ApiResponse<List<PopularTvShowsResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<PopularTvShowsResponse>>>()

        ApiConfig.getApiService().getPopularTvShows().enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
            ) {
                resultTvShows.value = ApiResponse.success(response.body()?.results as List<PopularTvShowsResponse>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d("RemoteDataSource ", "onFailure: ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultTvShows
    }

    fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<TvShowDetailResponse>>()

        ApiConfig.getApiService().getTvShowDetail(tvShowId).enqueue(object : Callback<TvShowDetailResponse> {
            override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
            ) {
                resultTvShow.value = ApiResponse.success(response.body() as TvShowDetailResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                Log.d("RemoteDataSource ", "onFailure: ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultTvShow
    }
}