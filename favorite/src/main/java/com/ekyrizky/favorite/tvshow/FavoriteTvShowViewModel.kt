package com.ekyrizky.favorite.tvshow

import androidx.lifecycle.*
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.core.domain.usecase.TvShowUseCase
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.moviecatalogue.model.tvshow.FavoriteTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ekyrizky.core.utils.DataMapper as DataMapperCore

class FavoriteTvShowViewModel  @Inject constructor(private val tvShowUseCase: TvShowUseCase): ViewModel() {

    fun getFavoriteTvShow(): LiveData<List<FavoriteTvShow>> {
        return tvShowUseCase.getFavoriteTvShows().asLiveData().map { resource ->
            DataMapper.mapFavoriteTvShowDomainToFavoriteTvShow(resource)
        }
    }

    fun insertFavoriteTvShow(tvShowDetail: FavoriteTvShowDomain) {
        val tvShowValue = DataMapperCore.mapFavoriteTvShowDomainToEntity(tvShowDetail)
        viewModelScope.launch(Dispatchers.IO) {
            tvShowUseCase.insertFavoriteTvShow(tvShowValue)
        }
    }

    fun deleteFavoriteTvShow(favoriteTvShowDomain: FavoriteTvShowDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            val tvShowEntity = DataMapperCore.mapFavoriteTvShowDomainToEntity(favoriteTvShowDomain)
            tvShowUseCase.deleteFavoriteTvShow(tvShowEntity)
        }
    }
}