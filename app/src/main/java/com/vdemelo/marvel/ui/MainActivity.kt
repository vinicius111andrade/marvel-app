package com.vdemelo.marvel.ui

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupToolbarBackButton()
        observeInternetConnectionStatus()
        observeCheckConnectionTrigger()
        setContentView(binding.root)
    }

    private fun setupToolbarBackButton() {
        binding.toolbarBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeCheckConnectionTrigger() {
        viewModel.triggerCheckConnection.observe(this) { shouldTrigger ->
            if (shouldTrigger) checkForInternetConnection()
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
}
