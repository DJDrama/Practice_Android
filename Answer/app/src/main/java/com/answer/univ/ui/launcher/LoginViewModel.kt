package com.answer.univ.ui.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.answer.univ.repository.LauncherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel
@ViewModelInject
constructor(
    private val launcherRepository: LauncherRepository
) : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get()=_loginSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = launcherRepository.login(email, password)
            result.error?.let {
                _error.postValue(it)
            }?: if(result.isSuccess){
                _loginSuccess.postValue(true)
            }
        }
    }
}