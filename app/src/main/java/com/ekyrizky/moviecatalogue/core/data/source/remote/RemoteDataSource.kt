package com.ekyrizky.moviecatalogue.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiService
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowResponse
import com.ekyrizky.moviecatalogue.core.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RemoteDataSource(service)
        }
    }

    fun getMovies(): LiveData<ApiResponse<List<PopularMoviesResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<PopularMoviesResponse>>>()
        val client = apiService.getPopularMovies()

        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
            ) {
                resultMovies.value = ApiResponse.Success(response.body()?.results as List<PopularMoviesResponse>)
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
        val client = apiService.getMovieDetail(movieId)

        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                resultMovie.value = ApiResponse.Success(response.body() as MovieDetailResponse)
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
        val client = apiService.getPopularTvShows()

        client.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
            ) {
                resultTvShows.value = ApiResponse.Success(response.body()?.results as List<PopularTvShowsResponse>)
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
        val client = apiService.getTvShowDetail(tvShowId)

       client.enqueue(object : Callback<TvShowDetailResponse> {
            override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
            ) {
                resultTvShow.value = ApiResponse.Success(response.body() as TvShowDetailResponse)
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