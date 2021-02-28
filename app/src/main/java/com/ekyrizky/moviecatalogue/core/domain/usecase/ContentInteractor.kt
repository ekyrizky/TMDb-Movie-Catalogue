package com.ekyrizky.moviecatalogue.core.domain.usecase

import androidx.lifecycle.LiveData
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.repository.IContentRepository

class ContentInteractor(private val contentRepository: IContentRepository): ContentUseCase {

    override fun getMovies(sort: String): LiveData<Resource<List<MovieDomain>>> =
            contentRepository.getMovies(sort)

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDomain>> =
            contentRepository.getMovieDetail(movieId)

    override fun getFavoriteMovies(): LiveData<List<MovieDomain>> =
            contentRepository.getFavoriteMovies()

    override fun setFavoriteMovie(movie: MovieDomain, state: Boolean) =
            contentRepository.setFavoriteMovie(movie, state)

    override fun getTvShows(sort: String): LiveData<Resource<List<TvShowDomain>>> =
            contentRepository.getTvShows(sort)

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowDomain>> =
            contentRepository.getTvShowDetail(tvShowId)

    override fun getFavoriteTvShows(): LiveData<List<TvShowDomain>> =
            contentRepository.getFavoriteTvShows()

    override fun setFavoriteTvShow(tvShow: TvShowDomain, state: Boolean) =
            contentRepository.setFavoriteTvShow(tvShow, state)
}