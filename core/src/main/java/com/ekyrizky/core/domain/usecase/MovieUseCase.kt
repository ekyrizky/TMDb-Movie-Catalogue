package com.ekyrizky.core.domain.usecase

import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.core.domain.model.movie.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovies(sort: String): Flow<Resource<List<MovieDomain>>>

    fun getPopularMovies(): Flow<Resource<List<MovieDomain>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomain>>

    fun getFavoriteMovies(): Flow<List<FavoriteMovieDomain>>

    suspend fun getSearchMovie(query: String): Resource<List<MovieDomain>>

    suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity)

    suspend fun checkFavoriteMovie(id: Int): Boolean

    suspend fun deleteFavoriteMovieById(id: Int)

    suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity)
}