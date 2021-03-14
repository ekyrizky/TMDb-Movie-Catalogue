package com.ekyrizky.core.domain.usecase

import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.core.domain.model.movie.MovieDomain
import com.ekyrizky.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getMovies(sort: String): Flow<Resource<List<MovieDomain>>> =
            movieRepository.getMovies(sort)

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomain>> =
            movieRepository.getMovieDetail(movieId)

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieDomain>> =
            movieRepository.getFavoriteMovies()

    override suspend fun getSearchMovie(query: String): Resource<List<MovieDomain>> =
            movieRepository.getSearchMovies(query)

    override suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            movieRepository.insertFavoriteMovie(favoriteMovies)

    override suspend fun checkFavoriteMovie(id: Int): Boolean =
            movieRepository.checkFavoriteMovie(id)

    override suspend fun deleteFavoriteMovieById(id: Int) =
            movieRepository.deleteFavoriteMovieById(id)

    override suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            movieRepository.deleteFavoriteMovie(favoriteMovies)
}