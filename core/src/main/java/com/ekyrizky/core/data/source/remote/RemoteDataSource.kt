package com.ekyrizky.core.data.source.remote

import android.util.Log
import com.ekyrizky.core.data.source.remote.network.ApiResponse
import com.ekyrizky.core.data.source.remote.network.ApiService
import com.ekyrizky.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.core.data.source.remote.response.movie.MovieResultResponse
import com.ekyrizky.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.core.data.source.remote.response.tvshow.TvShowResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResultResponse>>> {
        return flow {
            try {
                val response = apiService.getPopularMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                val response = apiService.getMovieDetail(movieId)
                emit(ApiResponse.Success(response))
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvShowResultResponse>>> {
        return flow {
            try {
                val response = apiService.getPopularTvShows()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            try {
                val response = apiService.getTvShowDetail(tvShowId)
                emit(ApiResponse.Success(response))
            } catch (e: java.lang.Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource ", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchMovie(query: String): Flow<ApiResponse<List<MovieResultResponse>>> {
        return flow {
            try {
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
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchTvShow(query: String): Flow<ApiResponse<List<TvShowResultResponse>>> {
        return flow {
            try {
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
            }
        }.flowOn(Dispatchers.IO)
    }
}