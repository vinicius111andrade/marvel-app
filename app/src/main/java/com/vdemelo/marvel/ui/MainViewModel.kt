package com.vdemelo.marvel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {

    private val _connectedToTheInternet = MutableLiveData<Boolean>(true)
    val connectedToTheInternet: LiveData<Boolean> get() = _connectedToTheInternet

    suspend fun updateInternetStatus(isConnected: Boolean) {
        _connectedToTheInternet.postValue(isConnected)
    }
}
