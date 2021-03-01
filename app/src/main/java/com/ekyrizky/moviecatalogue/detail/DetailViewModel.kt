package com.ekyrizky.moviecatalogue.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataMapper
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_TVSHOW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val contentUseCase: ContentUseCase): ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieDetailDomain>>
    private lateinit var detailTvShow: LiveData<Resource<TvShowDetailDomain>>

    fun setContent(id: String, category: String) {
        when (category) {
            EXTRA_MOVIE -> {
                detailMovie = contentUseCase.getMovieDetail(id.toInt()).asLiveData()
            }

            EXTRA_TVSHOW -> {
                detailTvShow = contentUseCase.getTvShowDetail(id.toInt()).asLiveData()
            }
        }
    }

    fun getMovieDetail() = detailMovie

    fun insertFavoriteMovie(movieDetail: MovieDetailDomain) {
        val movieValue = DataMapper.mapDetailMovieDomainToFavoriteEntity(movieDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteMovie(movieValue)
        }
    }

    suspend fun checkFavoriteMovie(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            contentUseCase.checkFavoriteMovie(id)
        }
    }

    fun deleteFavoriteMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.deleteFavoriteMovieById(id)
        }
    }

    fun getTvShowDetail() = detailTvShow

    fun insertFavoriteTvShow(tvShowDetail: TvShowDetailDomain) {
        val tvShowValue = DataMapper.mapDetailTvShowDomainToFavoriteEntity(tvShowDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteTvShow(tvShowValue)
        }
    }

    suspend fun checkFavoriteTvShow(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            contentUseCase.checkFavoriteTvShow(id)
        }
    }

    fun deleteFavoriteTvShowById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.deleteFavoriteTvShowById(id)
        }
    }
}