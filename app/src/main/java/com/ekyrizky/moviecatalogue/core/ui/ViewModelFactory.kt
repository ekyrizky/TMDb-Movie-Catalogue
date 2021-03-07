package com.ekyrizky.moviecatalogue.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.moviecatalogue.core.di.Injection
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.detail.movie.MovieDetailViewModel
import com.ekyrizky.moviecatalogue.detail.tvshow.TvShowDetailViewModel
import com.ekyrizky.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import com.ekyrizky.moviecatalogue.movie.MovieViewModel
import com.ekyrizky.moviecatalogue.search.SearchViewModel
import com.ekyrizky.moviecatalogue.tvshow.TvShowViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

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

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(TvShowDetailViewModel::class.java) -> {
                TvShowDetailViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(contentUseCase) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(contentUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}