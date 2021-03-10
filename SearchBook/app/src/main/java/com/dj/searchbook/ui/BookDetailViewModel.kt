package com.dj.searchbook.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dj.searchbook.data.model.Document

class BookDetailViewModel : ViewModel() {
    private val _detailDocument = MutableLiveData<Document?>()
    val detailDocument: LiveData<Document?>
        get() = _detailDocument

    fun setFavorite() {
        detailDocument.value?.let {
            it.isFavorite = !it.isFavorite
            setDetailDocument(it)
        }
    }

    fun setDetailDocument(document: Document) {
        _detailDocument.value = document
    }

    fun getIsFavorite() = detailDocument.value?.isFavorite ?: false
}
