package com.vdemelo.marvel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.vdemelo.marvel.ui.navigation.AppNavHost
import com.vdemelo.marvel.ui.navigation.NavItem
import com.vdemelo.marvel.ui.theme.ComposeMarvelTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMarvelTheme {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    startDestination = NavItem.Home.route
                )
            }
        }
    }
}
