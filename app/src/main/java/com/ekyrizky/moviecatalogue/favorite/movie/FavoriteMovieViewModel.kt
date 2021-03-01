package com.ekyrizky.moviecatalogue.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekyrizky.moviecatalogue.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(private val contentUseCase: ContentUseCase): ViewModel() {
    fun getFavoriteMovies() =
            contentUseCase.getFavoriteMovies().asLiveData()

    fun insertFavoriteMovie(movieDetail: FavoriteMovieDomain) {
        val movieValue = DataMapper.mapFavoriteMovieDomainToEntity(movieDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteMovie(movieValue)
        }
    }

    fun deleteFavoriteMovie(favoriteMovieDomain: FavoriteMovieDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieEntity =
                    DataMapper.mapFavoriteMovieDomainToEntity(favoriteMovieDomain)
            contentUseCase.deleteFavoriteMovie(movieEntity)
        }
    }
}