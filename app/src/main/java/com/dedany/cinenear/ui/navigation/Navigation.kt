package com.dedany.cinenear.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dedany.cinenear.ui.common.NavArgs
import com.dedany.cinenear.ui.common.NavScreen
import com.dedany.cinenear.ui.screens.detail.DetailScreen
import com.dedany.cinenear.ui.screens.home.HomeScreen
import com.dedany.cinenear.ui.screens.home.HomeScreenContainer

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreenContainer(
                onMovieClick = { movieId ->
                    navController.navigate(NavScreen.Detail.createRoute(movieId))
                }
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
