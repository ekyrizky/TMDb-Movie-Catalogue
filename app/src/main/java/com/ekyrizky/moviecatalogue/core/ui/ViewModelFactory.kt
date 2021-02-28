package com.ekyrizky.moviecatalogue.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.moviecatalogue.core.di.Injection
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.detail.DetailViewModel
import com.ekyrizky.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import com.ekyrizky.moviecatalogue.movie.MovieViewModel
import com.ekyrizky.moviecatalogue.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val contentUseCase: ContentUseCase):
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideContentUseCase(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(contentUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}