package com.ekyrizky.favorite.tvshow

import androidx.lifecycle.*
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.tvshow.FavoriteTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ekyrizky.core.utils.DataMapper as DataMapperCore
import com.ekyrizky.moviecatalogue.utils.DataMapper as DataMapperApp

class FavoriteTvShowViewModel  @Inject constructor(private val contentUseCase: ContentUseCase): ViewModel() {

    fun getFavoriteTvShow(): LiveData<List<FavoriteTvShow>> {
        return contentUseCase.getFavoriteTvShows().asLiveData().map { resource ->
            DataMapper.mapFavoriteTvShowDomainToFavoriteTvShow(resource)
        }
    }

    fun insertFavoriteTvShow(tvShowDetail: FavoriteTvShowDomain) {
        val tvShowValue = DataMapperCore.mapFavoriteTvShowDomainToEntity(tvShowDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteTvShow(tvShowValue)
        }
    }

    fun deleteFavoriteTvShow(favoriteTvShowDomain: FavoriteTvShowDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            val tvShowEntity = DataMapperCore.mapFavoriteTvShowDomainToEntity(favoriteTvShowDomain)
            contentUseCase.deleteFavoriteTvShow(tvShowEntity)
        }
    }
}