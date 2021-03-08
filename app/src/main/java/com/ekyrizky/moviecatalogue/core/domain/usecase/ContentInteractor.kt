package com.ekyrizky.moviecatalogue.core.domain.usecase

import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.movie.FavoriteMovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.moviecatalogue.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.repository.IContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentInteractor @Inject constructor(private val contentRepository: IContentRepository): ContentUseCase {

    override fun getMovies(sort: String): Flow<Resource<List<MovieDomain>>> =
            contentRepository.getMovies(sort)

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomain>> =
            contentRepository.getMovieDetail(movieId)

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieDomain>> =
            contentRepository.getFavoriteMovies()

    override suspend fun getSearchMovie(query: String): Resource<List<MovieDomain>> =
            contentRepository.getSearchMovies(query)

    override suspend fun insertFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            contentRepository.insertFavoriteMovie(favoriteMovies)

    override suspend fun checkFavoriteMovie(id: Int): Boolean =
            contentRepository.checkFavoriteMovie(id)

    override suspend fun deleteFavoriteMovieById(id: Int) =
            contentRepository.deleteFavoriteMovieById(id)

    override suspend fun deleteFavoriteMovie(favoriteMovies: FavoriteMovieEntity) =
            contentRepository.deleteFavoriteMovie(favoriteMovies)

    override fun getTvShows(sort: String): Flow<Resource<List<TvShowDomain>>> =
            contentRepository.getTvShows(sort)

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetailDomain>> =
            contentRepository.getTvShowDetail(tvShowId)

    override fun getFavoriteTvShows(): Flow<List<FavoriteTvShowDomain>> =
            contentRepository.getFavoriteTvShows()

    override suspend fun getSearchTvShow(query: String): Resource<List<TvShowDomain>> =
            contentRepository.getSearchTvShows(query)

    override suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
            contentRepository.insertFavoriteTvShow(favoriteTvShow)

    override suspend fun checkFavoriteTvShow(id: Int): Boolean =
            contentRepository.checkFavoriteTvShow(id)

    override suspend fun deleteFavoriteTvShowById(id: Int) =
            contentRepository.deleteFavoriteTvShowById(id)

    override suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
            contentRepository.deleteFavoriteTvShow(favoriteTvShow)
}