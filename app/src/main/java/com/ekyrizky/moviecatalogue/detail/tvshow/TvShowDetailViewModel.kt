package com.ekyrizky.moviecatalogue.detail.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvShowDetailViewModel  @Inject constructor(private val contentUseCase: ContentUseCase): ViewModel() {

    fun getTvShowDetail(id: String) = contentUseCase.getTvShowDetail(id.toInt()).asLiveData()

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