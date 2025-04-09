package com.dedany.cinenear.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dedany.cinenear.ui.screens.detail.DetailScreen
import com.dedany.cinenear.ui.screens.home.HomeScreen
import androidx.navigation.compose.NavHost
import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.data.RegionRepository
import com.dedany.cinenear.data.datasource.LocationDataSource
import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.RegionDataSource
import com.dedany.cinenear.ui.screens.detail.DetailViewModel
import com.dedany.cinenear.ui.screens.home.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as Application
    val moviesRepository = MoviesRepository(
        RegionRepository(
            RegionDataSource(app, LocationDataSource(app))),
            MoviesRemoteDataSource()
    )
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                },
                viewModel { HomeViewModel(moviesRepository) }
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                viewModel { DetailViewModel(movieId, moviesRepository) },
                onBack = { navController.popBackStack() })
        }
    }
}