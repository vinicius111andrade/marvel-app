package com.vdemelo.common.extensions

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

fun AppCompatActivity.setupNavigation(
    @IdRes navHostFragmentId: Int,
    @NavigationRes graphResId: Int
) {
    val navHostFragment =
        supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment

    val navController = navHostFragment.navController
    navController.setGraph(graphResId)

    //TODO to when I setup an action bar
//    val appBarConfiguration = AppBarConfiguration(navController.graph)
//    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
}
