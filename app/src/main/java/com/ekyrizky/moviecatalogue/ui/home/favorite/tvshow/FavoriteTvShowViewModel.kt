package com.ekyrizky.moviecatalogue.ui.home.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val contentRepository: ContentRepository): ViewModel() {
    fun getFavoriteTvShow() =
            contentRepository.getFavoriteTvShows()

    fun setFavoriteTvShow(tvShowEntity: TvShowEntity) {
        val newState = !tvShowEntity.favorited
        contentRepository.setFavoriteTvShow(tvShowEntity, newState)
    }
}