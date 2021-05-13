package com.movierecom.www.api

import com.movierecom.www.model.TmdbMovie

data class TmdbMovieResponse(
 val results: List<TmdbMovie>
)