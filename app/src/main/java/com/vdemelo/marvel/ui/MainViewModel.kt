package com.vdemelo.marvel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TEN_SECONDS = 10000L

class MainViewModel : ViewModel() {

    private val _triggerCheckConnection = MutableLiveData<Boolean>(false)
    val triggerCheckConnection: LiveData<Boolean> get() = _triggerCheckConnection

    private val _connectedToTheInternet = MutableLiveData<Boolean>(true)
    val connectedToTheInternet: LiveData<Boolean> get() = _connectedToTheInternet

    init {
        viewModelScope.launch {
            while (true) {
                _triggerCheckConnection.postValue(true)
                delay(TEN_SECONDS)
            }
        }
    }

    fun updateInternetStatus(isConnected: Boolean) {
        _connectedToTheInternet.postValue(isConnected)
        _triggerCheckConnection.postValue(false)
    }
}
