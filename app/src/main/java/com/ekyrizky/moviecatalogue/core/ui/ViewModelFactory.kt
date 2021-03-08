package com.ekyrizky.moviecatalogue.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.detail.movie.MovieDetailViewModel
import com.ekyrizky.moviecatalogue.detail.tvshow.TvShowDetailViewModel
import com.ekyrizky.moviecatalogue.di.AppScope
import com.ekyrizky.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import com.ekyrizky.moviecatalogue.movie.MovieViewModel
import com.ekyrizky.moviecatalogue.search.SearchViewModel
import com.ekyrizky.moviecatalogue.tvshow.TvShowViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return creator.get() as T
    }
}