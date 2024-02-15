package com.vdemelo.marvel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
