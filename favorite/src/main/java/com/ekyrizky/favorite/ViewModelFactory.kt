package com.ekyrizky.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.core.domain.usecase.ContentUseCase
import com.ekyrizky.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.favorite.tvshow.FavoriteTvShowViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val contentUseCase: ContentUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(contentUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}