package com.ekyrizky.moviecatalogue.core.data.source.remote

import android.util.Log
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiService
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieResultResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowResultResponse
import com.ekyrizky.moviecatalogue.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RemoteDataSource(service)
        }
    }

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResultResponse>>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getPopularMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getMovieDetail(movieId)
                emit(ApiResponse.Success(response))
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvShowResultResponse>>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getPopularTvShows()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.getTvShowDetail(tvShowId)
                emit(ApiResponse.Success(response))
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchMovie(query: String): Flow<ApiResponse<List<MovieResultResponse>>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.searchMovies(query)
                val dataArray = response.results
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchTvShow(query: String): Flow<ApiResponse<List<TvShowResultResponse>>> {
        return flow {
            try {
                EspressoIdlingResource.increment()
                val response = apiService.searchTvShows(query)
                val dataArray = response.results
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }
}