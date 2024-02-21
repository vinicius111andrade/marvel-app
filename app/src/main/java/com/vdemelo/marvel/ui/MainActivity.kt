package com.vdemelo.marvel.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityManager: ConnectivityManager

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupToolbarBackButton()
        observeInternetConnectionStatus()
        setContentView(binding.root)
        checkForInternetConnection()
    }

    override fun onResume() {
        super.onResume()
        registerConnectivityManagerCallback()
    }

    override fun onPause() {
        super.onPause()
        unregisterConnectivityManagerCallback()
    }

    private fun setupToolbarBackButton() {
        binding.toolbarBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun checkForInternetConnection() {
        val isConnected: Boolean = isInternetConnected()
        viewModel.updateInternetStatus(isConnected)
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun observeInternetConnectionStatus() {
        viewModel.connectedToTheInternet.observe(this) { isConnected ->
            if (isConnected) {
                with(binding) {
                    internetStatusIcon.setImageResource(R.drawable.ic_green)
                    internetStatusText.text = getString(R.string.common_online)
                }
            } else {
                with(binding) {
                    internetStatusIcon.setImageResource(R.drawable.ic_red)
                    internetStatusText.text = getString(R.string.common_offline)
                }
            }
        }
    }

    private fun unregisterConnectivityManagerCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun registerConnectivityManagerCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            viewModel.updateInternetStatus(true)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            viewModel.updateInternetStatus(false)
        }
    }
}
