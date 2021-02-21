package com.ekyrizky.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_TVSHOW
import com.ekyrizky.moviecatalogue.vo.Resource

class DetailViewModel(private val contentRepository: ContentRepository): ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>
    private lateinit var detailTvShow: LiveData<Resource<TvShowEntity>>

    fun setContent(id: String, category: String) {
        when (category) {
            EXTRA_MOVIE -> {
                detailMovie = contentRepository.getMovieDetail(id.toInt())
            }

            EXTRA_TVSHOW -> {
                detailTvShow = contentRepository.getTvShowDetail(id.toInt())
            }
        }
    }

    fun getMovieDetail() = detailMovie

    fun getTvShowDetail() = detailTvShow

    fun setFavoriteMovie() {
        val resource = detailMovie.value
        if (resource?.data != null) {
            val newState = !resource.data.favorited
            contentRepository.setFavoriteMovie(resource.data, newState)
        }
    }

    fun setFavoriteTvShow() {
        val resource = detailTvShow.value
        if (resource?.data != null) {
            val newState = !resource.data.favorited
            contentRepository.setFavoriteTvShow(resource.data, newState)
        }
    }
}