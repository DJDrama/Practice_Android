package com.answer.univ.ui.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.answer.univ.repository.LauncherRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RegisterViewModel
@ViewModelInject
constructor(
    private val launcherRepository: LauncherRepository
) : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> get()=_registerSuccess

    private val _universities = MutableLiveData<Set<String>>()
    val universities: LiveData<Set<String>> get() = _universities

    private val _interests = MutableLiveData<List<String>>()
    val interests: LiveData<List<String>> get() = _interests

    init {
        _universities.value = launcherRepository.getUniversities()
        _interests.value = launcherRepository.getInterests()
    }


    fun register(
        email: String,
        password: String,
        name: String,
        nickName: String,
        phoneNumber: String,
        university: String,
        major: String,
        interest: String
    ) {
        viewModelScope.launch(IO){
           val result = launcherRepository.register(
                email,
                password,
                name,
                nickName,
                phoneNumber,
                university,
                major,
                interest
            )
            result.error?.let{
                _error.postValue(it)
            } ?: if(result.isSuccess){
                _registerSuccess.postValue(true)
            }
        }
    }
}

