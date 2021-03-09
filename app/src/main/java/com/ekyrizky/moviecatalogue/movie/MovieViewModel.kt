package com.ekyrizky.moviecatalogue.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.model.movie.Movie
import com.ekyrizky.moviecatalogue.utils.DataMapper
import javax.inject.Inject

class MovieViewModel  @ViewModelInject constructor(private val contentUseCase: ContentUseCase) : ViewModel() {
    fun getMovies(sort: String): LiveData<Resource<List<Movie>>> {
        return contentUseCase.getMovies(sort).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapMovieDomainToMovie(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }
}