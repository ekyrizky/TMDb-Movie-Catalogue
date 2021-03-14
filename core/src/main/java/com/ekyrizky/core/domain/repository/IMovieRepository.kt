package com.ekyrizky.core.domain.repository

import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.core.domain.model.movie.MovieDomain
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovies(sort: String): Flow<Resource<List<MovieDomain>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomain>>

    fun getFavoriteMovies(): Flow<List<FavoriteMovieDomain>>

    suspend fun getSearchMovies(query: String): Resource<List<MovieDomain>>

    suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity)

    suspend fun checkFavoriteMovie(id: Int): Boolean

    suspend fun deleteFavoriteMovieById(id: Int)

    suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity)
}