package com.ekyrizky.moviecatalogue.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel  @Inject constructor(private val contentUseCase: ContentUseCase) : ViewModel() {
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
        }.asLiveData(viewModelScope.coroutineContext)

    val searchTvShowResult = queryChannel.asFlow()
        .debounce(debounceDuration)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            contentUseCase.getSearchTvShow(it)
        }.asLiveData(viewModelScope.coroutineContext)
}