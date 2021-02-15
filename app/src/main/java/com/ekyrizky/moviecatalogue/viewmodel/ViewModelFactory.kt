package com.ekyrizky.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.di.Injection
import com.ekyrizky.moviecatalogue.ui.content.movie.MovieViewModel
import com.ekyrizky.moviecatalogue.ui.content.tvshow.TvShowViewModel
import com.ekyrizky.moviecatalogue.ui.detail.DetailViewModel

class ViewModelFactory private constructor(private val mContentRepository: ContentRepository):
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideContentRepository())
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
            else -> throw Throwable("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}