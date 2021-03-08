package com.ekyrizky.moviecatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvShowViewModel  @Inject constructor(private val contentUseCase: ContentUseCase): ViewModel() {

    fun getFavoriteTvShow() = contentUseCase.getFavoriteTvShows().asLiveData()

    fun insertFavoriteTvShow(tvShowDetail: FavoriteTvShowDomain) {
        val tvShowValue = DataMapper.mapFavoriteTvShowDomainToEntity(tvShowDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteTvShow(tvShowValue)
        }
    }

    fun deleteFavoriteTvShow(favoriteTvShowDomain: FavoriteTvShowDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            val tvShowEntity = DataMapper.mapFavoriteTvShowDomainToEntity(favoriteTvShowDomain)
            contentUseCase.deleteFavoriteTvShow(tvShowEntity)
        }
    }
}