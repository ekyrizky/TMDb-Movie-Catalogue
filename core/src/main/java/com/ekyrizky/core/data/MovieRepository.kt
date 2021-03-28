package com.ekyrizky.core.data

import com.ekyrizky.core.data.source.local.LocalDataSource
import com.ekyrizky.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.core.data.source.remote.RemoteDataSource
import com.ekyrizky.core.data.source.remote.network.ApiResponse
import com.ekyrizky.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.core.data.source.remote.response.movie.MovieResultResponse
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.core.domain.model.movie.MovieDomain
import com.ekyrizky.core.domain.repository.IMovieRepository
import com.ekyrizky.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): IMovieRepository {

    override fun getMovies(): Flow<Resource<List<MovieDomain>>> {
        return object : NetworkBoundResource<List<MovieDomain>, List<MovieResultResponse>>() {
            override fun loadFromDB(): Flow<List<MovieDomain>> {
                return localDataSource.getMovies().map { DataMapper.mapMovieEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<MovieDomain>?): Boolean =
                    data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResultResponse>>> =
                    remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResultResponse>) {
                val movieList = DataMapper.mapMoviesResponseToEntity(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getPopularMovies(): Flow<Resource<List<MovieDomain>>> {
        return object : NetworkBoundResource<List<MovieDomain>, List<MovieResultResponse>>() {
            override fun loadFromDB(): Flow<List<MovieDomain>> {
                return localDataSource.getPopularMovies().map { DataMapper.mapPopularMovieEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<MovieDomain>?): Boolean =
                    data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResultResponse>>> =
                    remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<MovieResultResponse>) {
                val movieList = DataMapper.mapMoviesResponseToPopularMovieEntity(data)
                localDataSource.insertPopularMovies(movieList)
            }
        }.asFlow()
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomain>> {
        return object : NetworkBoundResource<MovieDetailDomain, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<MovieDetailDomain> {
                return localDataSource.getMovieById(movieId).map { DataMapper.mapMovieDetailEntityToDomain(it) }
            }

            override fun shouldFetch(data: MovieDetailDomain?): Boolean =
                    data?.id == null

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> =
                    remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                val movie = DataMapper.mapMovieDetailResponseToEntity(data)
                localDataSource.insertMovieDetail(movie)
            }
        }.asFlow()
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieDomain>> =
            localDataSource.getFavoriteMovies().map { DataMapper.mapFavoriteMovieEntityToDomain(it) }

    override suspend fun getSearchMovies(query: String): Resource<List<MovieDomain>> {
        return when (val apiResponse = remoteDataSource.getSearchMovie(query).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapMoviesResponseToDomain(apiResponse.data)
                Resource.Success(result)
            }
            is ApiResponse.Empty -> Resource.Error(apiResponse.toString())
            is ApiResponse.Error -> Resource.Error(apiResponse.errorMessage)
        }
    }

    override suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            localDataSource.insertFavoriteMovie(favoriteMovies)

    override suspend fun checkFavoriteMovie(id: Int): Boolean =
            localDataSource.checkFavoriteMovie(id)

    override suspend fun deleteFavoriteMovieById(id: Int) =
            localDataSource.deleteFavoriteMovieById(id)

    override suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            localDataSource.deleteFavoriteMovie(favoriteMovies)
}