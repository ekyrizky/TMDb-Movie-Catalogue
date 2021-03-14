package com.ekyrizky.favorite.movie

import androidx.lifecycle.*
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.usecase.MovieUseCase
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.movie.FavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ekyrizky.core.utils.DataMapper as DataMapperCore

class FavoriteMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return movieUseCase.getFavoriteMovies().asLiveData().map { resource ->
            DataMapper.mapFavoriteMovieDomainToFavoriteMovie(resource)
        }
    }

    fun insertFavoriteMovie(movieDetail: FavoriteMovieDomain) {
        val movieValue = DataMapperCore.mapFavoriteMovieDomainToEntity(movieDetail)
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.insertFavoriteMovie(movieValue)
        }
    }

    fun deleteFavoriteMovie(favoriteMovieDomain: FavoriteMovieDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieEntity = DataMapperCore.mapFavoriteMovieDomainToEntity(favoriteMovieDomain)
            movieUseCase.deleteFavoriteMovie(movieEntity)
        }
    }
}