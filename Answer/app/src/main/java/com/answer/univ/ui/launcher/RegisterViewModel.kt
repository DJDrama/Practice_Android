package com.answer.univ.ui.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.answer.univ.repository.LauncherRepository

class RegisterViewModel
@ViewModelInject
constructor(
    private val launcherRepository: LauncherRepository
) : ViewModel() {

    private val _universities = launcherRepository.getUniversities()
    val universities get() = _universities
}