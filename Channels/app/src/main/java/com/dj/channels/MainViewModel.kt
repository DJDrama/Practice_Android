package com.dj.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _channel = Channel<Int>()
    val channelFlow = _channel.receiveAsFlow()

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun setInt(value: Int) = viewModelScope.launch {
        _channel.send(value)
    }

    fun setIntForSharedFlow(value: Int) = viewModelScope.launch {
        _sharedFlow.emit(value)
    }


}