package com.ekyrizky.moviecatalogue.favorite.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.model.movie.FavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ekyrizky.core.utils.DataMapper as DataMapperCore
import com.ekyrizky.moviecatalogue.utils.DataMapper as DataMapperApp

class FavoriteMovieViewModel @ViewModelInject constructor(private val contentUseCase: ContentUseCase): ViewModel() {

    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return contentUseCase.getFavoriteMovies().asLiveData().map { resource ->
            DataMapperApp.mapFavoriteMovieDomainToFavoriteMovie(resource)
        }
    }

    fun insertFavoriteMovie(movieDetail: FavoriteMovieDomain) {
        val movieValue = DataMapperCore.mapFavoriteMovieDomainToEntity(movieDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteMovie(movieValue)
        }
    }

    fun deleteFavoriteMovie(favoriteMovieDomain: FavoriteMovieDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieEntity = DataMapperCore.mapFavoriteMovieDomainToEntity(favoriteMovieDomain)
            contentUseCase.deleteFavoriteMovie(movieEntity)
        }
    }
}