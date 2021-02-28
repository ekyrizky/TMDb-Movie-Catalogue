package com.ekyrizky.moviecatalogue.favorite.movie

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase

class FavoriteMovieViewModel(private val contentUseCase: ContentUseCase): ViewModel() {
    fun getFavoriteMovies() =
            contentUseCase.getFavoriteMovies()

    fun setFavoriteMovie(movieDomain: MovieDomain) {
        val newState = !movieDomain.isFavorite
        contentUseCase.setFavoriteMovie(movieDomain, newState)
    }
}