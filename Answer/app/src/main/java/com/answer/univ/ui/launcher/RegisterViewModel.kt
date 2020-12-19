package com.answer.univ.ui.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.answer.univ.repository.LauncherRepository

class RegisterViewModel
@ViewModelInject
constructor(
    private val launcherRepository: LauncherRepository
) : ViewModel() {

    private val _universities = MutableLiveData<Set<String>>()
    val universities: LiveData<Set<String>> get() = _universities

    private val _interests = MutableLiveData<List<String>>()
    val interests: LiveData<List<String>> get() = _interests

    init {
        _universities.value = launcherRepository.getUniversities()
        _interests.value = launcherRepository.getInterests()
    }

}