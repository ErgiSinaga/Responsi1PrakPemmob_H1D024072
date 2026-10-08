package com.ergi.digimon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ergi.digimon.ui.screens.DetailScreen
import com.ergi.digimon.ui.screens.HomeScreen

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{id}"
    fun detail(id: Int) = "detail/$id"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(onDigimonClick = { id -> navController.navigate(Routes.detail(id)) })
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
