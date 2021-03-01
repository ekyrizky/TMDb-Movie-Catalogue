package com.ekyrizky.moviecatalogue.core.domain.repository

import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import kotlinx.coroutines.flow.Flow

interface IContentRepository {

    fun getMovies(sort: String): Flow<Resource<List<MovieDomain>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomain>>

    fun getFavoriteMovies(): Flow<List<FavoriteMovieDomain>>

    suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity)

    suspend fun checkFavoriteMovie(id: Int): Boolean

    suspend fun deleteFavoriteMovieById(id: Int)

    suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity)

    suspend fun deleteAllFavoriteMovies()

    fun getTvShows(sort: String): Flow<Resource<List<TvShowDomain>>>

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetailDomain>>

    fun getFavoriteTvShows(): Flow<List<FavoriteTvShowDomain>>

    suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity)

    suspend fun checkFavoriteTvShow(id: Int): Boolean

    suspend fun deleteFavoriteTvShowById(id: Int)

    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity)

    suspend fun deleteAllFavoriteTvShows()

}