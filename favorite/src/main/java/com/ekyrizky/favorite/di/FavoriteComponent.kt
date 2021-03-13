package com.ekyrizky.favorite.di

import android.content.Context
import com.ekyrizky.favorite.movie.FavoriteMovieFragment
import com.ekyrizky.favorite.tvshow.FavoriteTvShowFragment
import com.ekyrizky.moviecatalogue.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class], modules = [ViewModelModule::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteMovieFragment)
    fun inject(fragment: FavoriteTvShowFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}