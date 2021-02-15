package com.ekyrizky.moviecatalogue.data.source.local.entity

data class ContentEntity(
    var id: Int = 0,
    var title: String?,
    var tagline: String?,
    var releaseYear: String?,
    var runtime: Int?,
    var voteAverage: Double?,
    var description: String?,
    var poster: String?,
    var backdrop: String?,
)
