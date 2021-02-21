package com.ekyrizky.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.di.Injection
import com.ekyrizky.moviecatalogue.ui.detail.DetailViewModel
import com.ekyrizky.moviecatalogue.ui.home.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.moviecatalogue.ui.home.favorite.tvshow.FavoriteTvShowViewModel
import com.ekyrizky.moviecatalogue.ui.home.movie.MovieViewModel
import com.ekyrizky.moviecatalogue.ui.home.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mContentRepository: ContentRepository):
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideContentRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(mContentRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}