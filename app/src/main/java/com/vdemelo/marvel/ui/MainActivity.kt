package com.vdemelo.marvel.ui

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var checkForInternetConnection = false

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupToolbarBackButton()
        observeInternetConnectionStatus()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        checkForInternetConnection = true
        checkForInternetConnectionLoop()
    }

    override fun onPause() {
        super.onPause()
        checkForInternetConnection = false
    }

    private fun setupToolbarBackButton() {
        binding.toolbarBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun checkForInternetConnectionLoop() {
        //TODO esse while true tá travando a UI thread, ver pq
//        runBlocking(Dispatchers.IO) {
//            while (checkForInternetConnection) {
//                val isConnected: Boolean = isInternetConnected()
//                viewModel.updateInternetStatus(isConnected)
//                delay(3000L)
//            }
//        }
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
