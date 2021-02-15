package com.ekyrizky.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ekyrizky.moviecatalogue.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FakeContentRepository (private val remoteDataSource: RemoteDataSource): ContentDataSource{

    override fun getNowPlayingMovies(): LiveData<List<MovieResponse>> {
        val movieResults = MutableLiveData<List<MovieResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getNowPlayingMovies(object : RemoteDataSource.LoadNowPlayingMoviesCallback {
                override fun onNowPlayingMoviesReceived(movieResponse: List<MovieResponse>) {
                    movieResults.postValue(movieResponse)
                }
            })
        }
        return movieResults
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieResponse> {
        val movieDetailResult = MutableLiveData<MovieResponse>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.LoadMovieDetailCallback {
                override fun onMovieDetailReceived(movieResponse: MovieResponse) {
                    movieDetailResult.postValue(movieResponse)
                }
            })
        }
        return movieDetailResult
    }

    override fun getOnTheAirTvShows(): LiveData<List<TvShowResponse>> {
        val tvShowResults = MutableLiveData<List<TvShowResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getOnTheAirTvShows(object : RemoteDataSource.LoadOnTheAirTvShowsCallback {
                override fun onOnAirTvShowsReceived(tvShowResponse: List<TvShowResponse>) {
                    tvShowResults.postValue(tvShowResponse)
                }
            })
        }
        return tvShowResults
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<TvShowResponse> {
        val tvShowDetailResult = MutableLiveData<TvShowResponse>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getTvShowDetail(tvShowId, object : RemoteDataSource.LoadTvShowDetailCallback {
                override fun onTvShowDetailReceived(tvShowResponse: TvShowResponse) {
                    tvShowDetailResult.postValue(tvShowResponse)
                }

            })
        }
        return tvShowDetailResult
    }
}