package com.ekyrizky.favorite.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.core.domain.usecase.MovieUseCase
import com.ekyrizky.core.domain.usecase.TvShowUseCase
import com.ekyrizky.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.favorite.tvshow.FavoriteTvShowViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val TvShowUseCase: TvShowUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(TvShowUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}