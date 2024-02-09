package com.vdemelo.marvel.ui.navigation

enum class Screen {
    HOME,
    CHARACTER,
    FAVORITES
}

sealed class NavItem(val route: String) {

    object Home : NavItem(route = Screen.HOME.name)
    object CharacterDetails : NavItem(route = Screen.CHARACTER.name)
    object Favorites : NavItem(route = Screen.FAVORITES.name)

}
