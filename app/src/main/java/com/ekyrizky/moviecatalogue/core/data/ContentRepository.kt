package com.ekyrizky.moviecatalogue.core.data

import com.ekyrizky.moviecatalogue.core.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.repository.IContentRepository
import com.ekyrizky.moviecatalogue.core.utils.AppExecutors
import com.ekyrizky.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContentRepository private constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
): IContentRepository {

    companion object {
        @Volatile
        private var INSTANCE: ContentRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource,
                        localDataSource: LocalDataSource,
                        appExecutors: AppExecutors
        ): ContentRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ContentRepository(remoteDataSource, localDataSource, appExecutors)
                }
    }

    override fun getMovies(sort: String): Flow<Resource<List<MovieDomain>>> {
        return object : NetworkBoundResource<List<MovieDomain>, List<PopularMoviesResponse>>() {
            override fun loadFromDB(): Flow<List<MovieDomain>> {
                return localDataSource.getMovies(sort).map { DataMapper.mapMovieEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<MovieDomain>?): Boolean =
                    data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<PopularMoviesResponse>>> =
                    remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<PopularMoviesResponse>) {
                val movieList = DataMapper.mapMoviesResponseToEntity(data)
                localDataSource.insertMovies(movieList)
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


    override suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            localDataSource.insertFavoriteMovie(favoriteMovies)


    override suspend fun checkFavoriteMovie(id: Int): Boolean =
            localDataSource.checkFavoriteMovie(id)


    override suspend fun deleteFavoriteMovieById(id: Int) =
            localDataSource.deleteFavoriteMovieById(id)


    override suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            localDataSource.deleteFavoriteMovie(favoriteMovies)


    override suspend fun deleteAllFavoriteMovies() =
            localDataSource.deleteAllFavoriteMovies()


    override fun getTvShows(sort: String): Flow<Resource<List<TvShowDomain>>> {
        return object : NetworkBoundResource<List<TvShowDomain>, List<PopularTvShowsResponse>>() {
            override fun loadFromDB(): Flow<List<TvShowDomain>> {
                return localDataSource.getTvShows(sort).map { DataMapper.mapTvShowEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<TvShowDomain>?): Boolean =
                    data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<PopularTvShowsResponse>>> =
                    remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<PopularTvShowsResponse>) {
                val tvShowList = DataMapper.mapTvShowsResponseToEntity(data)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetailDomain>> {
        return object : NetworkBoundResource<TvShowDetailDomain, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<TvShowDetailDomain> {
                return localDataSource.getTvShowById(tvShowId).map { DataMapper.mapTvShowDetailEntityToDomain(it) }
            }

            override fun shouldFetch(data: TvShowDetailDomain?): Boolean =
                data?.id == null

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> =
                    remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                val tvShow = DataMapper.mapTvShowDetailResponseToEntity(data)
                localDataSource.insertTvShowDetail(tvShow)
            }
        }.asFlow()
    }

    override fun getFavoriteTvShows(): Flow<List<FavoriteTvShowDomain>> =
            localDataSource.getFavoriteTvShows().map { DataMapper.mapFavoriteTvShowEntityToDomain(it) }


    override suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
            localDataSource.insertFavoriteTvShow(favoriteTvShow)


    override suspend fun checkFavoriteTvShow(id: Int): Boolean =
            localDataSource.checkFavoriteTvShow(id)


    override suspend fun deleteFavoriteTvShowById(id: Int) =
            localDataSource.deleteFavoriteTvShowById(id)


    override suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
            localDataSource.deleteFavoriteTvShow(favoriteTvShow)


    override suspend fun deleteAllFavoriteTvShows() =
            localDataSource.deleteAllFavoriteTvShows()

}