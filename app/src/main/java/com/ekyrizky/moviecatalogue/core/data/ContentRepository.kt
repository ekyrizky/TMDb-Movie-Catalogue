package com.ekyrizky.moviecatalogue.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ekyrizky.moviecatalogue.core.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.repository.IContentRepository
import com.ekyrizky.moviecatalogue.core.utils.AppExecutors
import com.ekyrizky.moviecatalogue.core.utils.DataMapper

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

    override fun getMovies(sort: String): LiveData<Resource<List<MovieDomain>>> {
        return object : NetworkBoundResource<List<MovieDomain>, List<PopularMoviesResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieDomain>> {
                return Transformations.map(localDataSource.getMovies(sort)) {
                    DataMapper.mapMovieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieDomain>?): Boolean =
                    data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<PopularMoviesResponse>>> =
                    remoteDataSource.getMovies()

            override fun saveCallResult(data: List<PopularMoviesResponse>) {
                val movieList = DataMapper.mapMoviesResponseToEntity(data)
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDomain>> {
        return object : NetworkBoundResource<MovieDomain, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieDomain> {
                return Transformations.map(localDataSource.getMovieById(movieId)) {
                    DataMapper.mapMovieEntityDetailToDomain(it)
                }
            }

            override fun shouldFetch(data: MovieDomain?): Boolean =
                    data?.tagline == "" && data.runtime == 0

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                    remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {

                val movie = DataMapper.mapMovieDetailResponseToEntity(data)
                localDataSource.updateMovie(movie, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<List<MovieDomain>> {
        return Transformations.map(localDataSource.getFavoriteMovies()) {
            DataMapper.mapMovieEntityToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: MovieDomain, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.updateMovie(movieEntity, state) }
    }


    override fun getTvShows(sort: String): LiveData<Resource<List<TvShowDomain>>> {
        return object : NetworkBoundResource<List<TvShowDomain>, List<PopularTvShowsResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowDomain>> {
                return Transformations.map(localDataSource.getTvShows(sort)) {
                    DataMapper.mapTvShowEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShowDomain>?): Boolean =
                    data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<PopularTvShowsResponse>>> =
                    remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<PopularTvShowsResponse>) {
                val tvShowList = DataMapper.mapMTvShowsResponseToEntity(data)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowDomain>> {
        return object : NetworkBoundResource<TvShowDomain, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowDomain> {
                return Transformations.map(localDataSource.getTvShowById(tvShowId)) {
                    DataMapper.mapTvShowEntityDetailToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShowDomain?): Boolean =
                    data?.tagline == "" && data.runtime == 0

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                    remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                val tvShow = DataMapper.mapTvShowDetailResponseToEntity(data)
                localDataSource.updateTvShow(tvShow, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<List<TvShowDomain>> {
        return Transformations.map(localDataSource.getFavoriteTvShows()) {
            DataMapper.mapTvShowEntityToDomain(it)
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShowDomain, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntity(tvShow)
        appExecutors.diskIO().execute { localDataSource.updateTvShow(tvShowEntity, state) }
    }
}