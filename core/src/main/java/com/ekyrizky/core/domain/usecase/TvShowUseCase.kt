package com.ekyrizky.core.domain.usecase

import com.ekyrizky.core.data.Resource
import com.ekyrizky.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDomain
import kotlinx.coroutines.flow.Flow

interface TvShowUseCase {

    fun getTvShows(sort: String): Flow<Resource<List<TvShowDomain>>>

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetailDomain>>

    fun getFavoriteTvShows(): Flow<List<FavoriteTvShowDomain>>

    suspend fun getSearchTvShow(query: String): Resource<List<TvShowDomain>>

    suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity)

    suspend fun checkFavoriteTvShow(id: Int): Boolean

    suspend fun deleteFavoriteTvShowById(id: Int)

    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity)
}