package com.ekyrizky.moviecatalogue.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_TVSHOW

class DetailViewModel(private val contentUseCase: ContentUseCase): ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieDomain>>
    private lateinit var detailTvShow: LiveData<Resource<TvShowDomain>>

    fun setContent(id: String, category: String) {
        when (category) {
            EXTRA_MOVIE -> {
                detailMovie = contentUseCase.getMovieDetail(id.toInt())
            }

            EXTRA_TVSHOW -> {
                detailTvShow = contentUseCase.getTvShowDetail(id.toInt())
            }
        }
    }

    fun getMovieDetail() = detailMovie

    fun getTvShowDetail() = detailTvShow

    fun setFavoriteMovie() {
        val resource = detailMovie.value
        if (resource?.data != null) {
            val newState = !resource.data.isFavorite
            contentUseCase.setFavoriteMovie(resource.data, newState)
        }
    }

    fun setFavoriteTvShow() {
        val resource = detailTvShow.value
        if (resource?.data != null) {
            val newState = !resource.data.isFavorite
            contentUseCase.setFavoriteTvShow(resource.data, newState)
        }
    }
}