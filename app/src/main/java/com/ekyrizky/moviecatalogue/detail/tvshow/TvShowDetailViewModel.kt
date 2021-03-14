package com.ekyrizky.moviecatalogue.detail.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.core.domain.usecase.TvShowUseCase
import com.ekyrizky.core.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.tvshow.TvShowDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowDetailViewModel  @ViewModelInject constructor(private val tvShowUseCase: TvShowUseCase): ViewModel() {

    fun getTvShowDetail(id: String): LiveData<Resource<TvShowDetail>> {
        return tvShowUseCase.getTvShowDetail(id.toInt()).asLiveData().map { resource ->
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
            tvShowUseCase.insertFavoriteTvShow(tvShowValue)
        }
    }

    suspend fun checkFavoriteTvShow(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            tvShowUseCase.checkFavoriteTvShow(id)
        }
    }

    fun deleteFavoriteTvShowById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowUseCase.deleteFavoriteTvShowById(id)
        }
    }
}