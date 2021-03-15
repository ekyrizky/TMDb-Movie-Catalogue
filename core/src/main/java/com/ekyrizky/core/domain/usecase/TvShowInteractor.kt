package com.ekyrizky.core.domain.usecase

import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.data.TvShowRepository
import com.ekyrizky.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowInteractor @Inject constructor(private val tvShowRepository: TvShowRepository): TvShowUseCase {

    override fun getTvShows(sort: String): Flow<Resource<List<TvShowDomain>>> =
        tvShowRepository.getTvShows(sort)

    override fun getPopularTvShows(): Flow<Resource<List<TvShowDomain>>> =
            tvShowRepository.getPopularTvShows()

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetailDomain>> =
        tvShowRepository.getTvShowDetail(tvShowId)

    override fun getFavoriteTvShows(): Flow<List<FavoriteTvShowDomain>> =
        tvShowRepository.getFavoriteTvShows()

    override suspend fun getSearchTvShow(query: String): Resource<List<TvShowDomain>> =
        tvShowRepository.getSearchTvShows(query)

    override suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
        tvShowRepository.insertFavoriteTvShow(favoriteTvShow)

    override suspend fun checkFavoriteTvShow(id: Int): Boolean =
        tvShowRepository.checkFavoriteTvShow(id)

    override suspend fun deleteFavoriteTvShowById(id: Int) =
        tvShowRepository.deleteFavoriteTvShowById(id)

    override suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
        tvShowRepository.deleteFavoriteTvShow(favoriteTvShow)
}