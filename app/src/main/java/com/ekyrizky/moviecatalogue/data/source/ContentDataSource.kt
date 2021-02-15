package com.ekyrizky.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse

interface ContentDataSource {
    fun getNowPlayingMovies(): LiveData<List<MovieResponse>>

    fun getMovieDetail(movieId: Int): LiveData<MovieResponse>

    fun getOnTheAirTvShows(): LiveData<List<TvShowResponse>>

    fun getTvShowDetail(tvShowId: Int): LiveData<TvShowResponse>
}