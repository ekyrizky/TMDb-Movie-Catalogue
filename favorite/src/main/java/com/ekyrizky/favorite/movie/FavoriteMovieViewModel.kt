package com.ekyrizky.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.ekyrizky.core.domain.usecase.MovieUseCase
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.movie.FavoriteMovie
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return movieUseCase.getFavoriteMovies().asLiveData().map { resource ->
            DataMapper.mapFavoriteMovieDomainToFavoriteMovie(resource)
        }
    }
}