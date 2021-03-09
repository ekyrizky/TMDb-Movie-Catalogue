package com.ekyrizky.moviecatalogue.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekyrizky.moviecatalogue.detail.movie.MovieDetailViewModel
import com.ekyrizky.moviecatalogue.detail.tvshow.TvShowDetailViewModel
import com.ekyrizky.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.ekyrizky.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import com.ekyrizky.moviecatalogue.movie.MovieViewModel
import com.ekyrizky.moviecatalogue.search.SearchViewModel
import com.ekyrizky.moviecatalogue.tvshow.TvShowViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowViewModel::class)
    abstract fun bindTvShowViewModel(viewModel: TvShowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieViewModel::class)
    abstract fun bindFavoriteMovieViewModel(viewModel: FavoriteMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteTvShowViewModel::class)
    abstract fun bindFavoriteTvShowViewModel(viewModel: FavoriteTvShowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailViewModel::class)
    abstract fun bindTvShowDetailViewModel(viewModel: TvShowDetailViewModel): ViewModel

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}