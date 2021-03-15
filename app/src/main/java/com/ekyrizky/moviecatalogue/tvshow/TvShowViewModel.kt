package com.ekyrizky.moviecatalogue.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.usecase.TvShowUseCase
import com.ekyrizky.moviecatalogue.model.tvshow.TvShow
import com.ekyrizky.moviecatalogue.utils.DataMapper

class TvShowViewModel  @ViewModelInject constructor(private val tvShowUseCase: TvShowUseCase): ViewModel() {
    fun getTvShows(sort: String): LiveData<Resource<List<TvShow>>> {
        return tvShowUseCase.getTvShows(sort).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val tvshows = DataMapper.mapTvShowDomainToTvShow(resource.data)
                    Resource.Success(tvshows)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun getPopularTvShows(): LiveData<Resource<List<TvShow>>> {
        return tvShowUseCase.getPopularTvShows().asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val tvshows = DataMapper.mapTvShowDomainToTvShow(resource.data)
                    Resource.Success(tvshows)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }
}