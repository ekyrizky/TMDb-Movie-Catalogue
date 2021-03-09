package com.ekyrizky.moviecatalogue.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel  @ViewModelInject constructor(private val contentUseCase: ContentUseCase) : ViewModel() {
    private var debounceDuration = 500L

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchMovieResult = queryChannel.asFlow()
        .debounce(debounceDuration)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            contentUseCase.getSearchMovie(it)
        }.asLiveData(viewModelScope.coroutineContext).map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val movies = DataMapper.mapMovieDomainToMovie(resource.data)
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }

    val searchTvShowResult = queryChannel.asFlow()
        .debounce(debounceDuration)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            contentUseCase.getSearchTvShow(it)
        }.asLiveData(viewModelScope.coroutineContext).map { resource ->
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