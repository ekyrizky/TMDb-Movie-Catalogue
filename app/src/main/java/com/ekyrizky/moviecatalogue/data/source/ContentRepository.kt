package com.ekyrizky.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ekyrizky.moviecatalogue.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.data.source.remote.ApiResponse
import com.ekyrizky.moviecatalogue.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.utils.AppExecutors
import com.ekyrizky.moviecatalogue.vo.Resource

class ContentRepository private constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
): ContentDataSource{

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

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<PopularMoviesResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(5)
                        .setPageSize(5)
                        .build()

                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                    data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<PopularMoviesResponse>>> =
                    remoteDataSource.getMovies()

            override fun saveCallResult(data: List<PopularMoviesResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                            id = response.id,
                            title = response.originalTitle,
                            tagline = null,
                            releaseYear = response.releaseDate,
                            runtime = null,
                            voteAverage = response.voteAverage,
                            description = response.overview,
                            posterPath = response.posterPath,
                            backdropPath = response.backdropPath,
                            favorited = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieEntity> =
                    localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                    (data?.tagline == null) && (data?.runtime == null)

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                    remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {
                val movie = MovieEntity(
                        id = data.id,
                        title = data.originalTitle,
                        tagline = data.tagline,
                        releaseYear = data.releaseDate,
                        runtime = data.runtime,
                        voteAverage = data.voteAverage,
                        description = data.overview,
                        posterPath = data.posterPath,
                        backdropPath = data.backdropPath,
                        favorited = false
                )
                localDataSource.updateMovie(movie, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()

        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
            appExecutors.diskIO().execute {
                localDataSource.updateMovie(movie, state)
            }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<PopularTvShowsResponse>>(appExecutors) {
            override fun loadFromDb(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()

                return LivePagedListBuilder(localDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                    data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<PopularTvShowsResponse>>> =
                    remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<PopularTvShowsResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                            id = response.id,
                            title = response.originalName,
                            tagline = null,
                            releaseYear = response.firstAirDate,
                            runtime = null,
                            voteAverage = response.voteAverage,
                            description = response.overview,
                            posterPath = response.posterPath,
                            backdropPath = response.backdropPath,
                            favorited = false
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<TvShowEntity> =
                    localDataSource.getTvShowById(tvShowId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                    data?.tagline == null && data?.runtime == null

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                    remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                val tvShow = TvShowEntity(
                        id = data.id,
                        title = data.originalName,
                        tagline = data.tagline,
                        releaseYear = data.firstAirDate,
                        runtime = data.episodeRunTime.first(),
                        voteAverage = data.voteAverage,
                        description = data.overview,
                        posterPath = data.posterPath,
                        backdropPath = data.backdropPath,
                        favorited = false
                )
                localDataSource.updateTvShow(tvShow, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()

        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) =
            appExecutors.diskIO().execute {
                localDataSource.updateTvShow(tvShow, state)
            }
}