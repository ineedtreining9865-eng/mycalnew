package com.example.mycalnew.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycalnew.ui.screens.DetailScreen
import com.example.mycalnew.ui.screens.HomeScreen

/**
 * Navigation routes used in the app.
 */
object Routes {
    const val Home = "home"
    const val Settings = "settings"
}

/**
 * Sets up the NavHost with composable destinations.
 */
@Composable
fun NavGraph(startDestination: String = Routes.Home) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Home) {
            HomeScreen(onNavigateToSettings = { navController.navigate(Routes.Settings) })
        }
        composable(Routes.Settings) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
