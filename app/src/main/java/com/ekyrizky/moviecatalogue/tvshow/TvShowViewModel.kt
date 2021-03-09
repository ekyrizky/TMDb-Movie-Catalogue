package com.ekyrizky.moviecatalogue.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.model.tvshow.TvShow
import com.ekyrizky.moviecatalogue.utils.DataMapper
import javax.inject.Inject

class TvShowViewModel  @ViewModelInject constructor(private val contentUseCase: ContentUseCase): ViewModel() {
    fun getTvShows(sort: String): LiveData<Resource<List<TvShow>>> {
        return contentUseCase.getTvShows(sort).asLiveData().map { resource ->
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