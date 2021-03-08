package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.moviecatalogue.MainActivity
import com.ekyrizky.moviecatalogue.core.di.CoreComponent
import com.ekyrizky.moviecatalogue.detail.movie.MovieDetailFragment
import com.ekyrizky.moviecatalogue.detail.tvshow.TvShowDetailFragment
import com.ekyrizky.moviecatalogue.favorite.movie.FavoriteMovieFragment
import com.ekyrizky.moviecatalogue.favorite.tvshow.FavoriteTvShowFragment
import com.ekyrizky.moviecatalogue.movie.MovieFragment
import com.ekyrizky.moviecatalogue.search.SearchFragment
import com.ekyrizky.moviecatalogue.search.SearchViewModel
import com.ekyrizky.moviecatalogue.tvshow.TvShowFragment
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AppScope
@Component(dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: MovieFragment)
    fun inject(fragment: TvShowFragment)
    fun inject(fragment: FavoriteMovieFragment)
    fun inject(fragment: FavoriteTvShowFragment)
    fun inject(fragment: MovieDetailFragment)
    fun inject(fragment: TvShowDetailFragment)
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun inject(fragment: SearchViewModel)
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun inject(fragment: SearchFragment)
}