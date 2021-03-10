package com.dj.searchbook.data.response

import com.dj.searchbook.data.model.Document
import com.dj.searchbook.data.model.Meta

data class SearchResponse(
    val meta: Meta,
    val documents: List<Document>
)
