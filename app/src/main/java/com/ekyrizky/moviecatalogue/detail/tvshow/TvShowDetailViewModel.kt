package com.ekyrizky.moviecatalogue.detail.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.core.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.tvshow.TvShowDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvShowDetailViewModel  @ViewModelInject constructor(private val contentUseCase: ContentUseCase): ViewModel() {

    fun getTvShowDetail(id: String): LiveData<Resource<TvShowDetail>> {
        return contentUseCase.getTvShowDetail(id.toInt()).asLiveData().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    val tvshows = com.ekyrizky.moviecatalogue.utils.DataMapper.mapTvShowDetailDomainToTvShowDetail(resource.data)
                    Resource.Success(tvshows)
                }
                is Resource.Error -> Resource.Error(resource.message.toString())
            }
        }
    }

    fun insertFavoriteTvShow(tvShowDetail: TvShowDetailDomain) {
        val tvShowValue = DataMapper.mapDetailTvShowDomainToFavoriteEntity(tvShowDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteTvShow(tvShowValue)
        }
    }

    suspend fun checkFavoriteTvShow(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            contentUseCase.checkFavoriteTvShow(id)
        }
    }

    fun deleteFavoriteTvShowById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.deleteFavoriteTvShowById(id)
        }
    }
}