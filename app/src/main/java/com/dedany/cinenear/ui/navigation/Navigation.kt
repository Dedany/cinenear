package com.dedany.cinenear.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dedany.cinenear.App
import com.dedany.cinenear.ui.screens.detail.DetailScreen
import com.dedany.cinenear.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App


    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                },
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                onBack = { navController.popBackStack() })
        }
    }
}