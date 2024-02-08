package com.vdemelo.marvel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavItem.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavItem.Home.route) {
            //HomeScreen(navController = navController)
        }

        composable(route = NavItem.MarvelCharacter.route) {
            //SpeciesListScreen(navController = navController)
        }

        composable(route = NavItem.Favorites.route) {
            //SpeciesListScreen(navController = navController)
        }
    }
}
