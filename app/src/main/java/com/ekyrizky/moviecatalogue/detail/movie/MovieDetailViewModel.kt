package com.ekyrizky.moviecatalogue.detail.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.core.domain.usecase.MovieUseCase
import com.ekyrizky.core.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.movie.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel  @ViewModelInject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMovieDetail(id: String): LiveData<Resource<MovieDetail>> {
        return movieUseCase.getMovieDetail(id.toInt()).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = com.ekyrizky.moviecatalogue.utils.DataMapper.mapMovieDetailDomainToMovieDetail(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun insertFavoriteMovie(movieDetail: MovieDetailDomain) {
        val movieValue = DataMapper.mapDetailMovieDomainToFavoriteEntity(movieDetail)
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.insertFavoriteMovie(movieValue)
        }
    }

    suspend fun checkFavoriteMovie(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            movieUseCase.checkFavoriteMovie(id)
        }
    }

    fun deleteFavoriteMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.deleteFavoriteMovieById(id)
        }
    }
}