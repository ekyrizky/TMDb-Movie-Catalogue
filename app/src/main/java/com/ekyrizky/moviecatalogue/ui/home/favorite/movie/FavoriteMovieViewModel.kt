package com.ekyrizky.moviecatalogue.ui.home.favorite.movie

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val contentRepository: ContentRepository): ViewModel() {
    fun getFavoriteMovies() =
        contentRepository.getFavoriteMovies()

    fun setFavoriteMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorited
        contentRepository.setFavoriteMovie(movieEntity, newState)
    }
}