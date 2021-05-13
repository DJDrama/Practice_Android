package com.movierecom.www.api

import com.movierecom.www.model.TmdbTrailer

data class TmdbTrailerResponse(
 val results: List<TmdbTrailer>
)