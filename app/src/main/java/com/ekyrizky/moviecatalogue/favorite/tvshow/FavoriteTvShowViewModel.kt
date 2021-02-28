package com.ekyrizky.moviecatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase

class FavoriteTvShowViewModel(private val contentUseCase: ContentUseCase): ViewModel() {
    fun getFavoriteTvShow() =
            contentUseCase.getFavoriteTvShows()

    fun setFavoriteTvShow(tvShowDomain: TvShowDomain) {
        val newState = !tvShowDomain.isFavorite
        contentUseCase.setFavoriteTvShow(tvShowDomain, newState)
    }
}