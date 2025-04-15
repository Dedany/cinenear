package com.dedany.cinenear.ui.navigation

import android.app.Application
import android.location.Geocoder
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
import com.dedany.cinenear.App
import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.data.RegionRepository
import com.dedany.cinenear.data.datasource.LocationDataSource
import com.dedany.cinenear.data.datasource.LocationDataSourceimpl
import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.MoviesRemoteDataSourceImpl
import com.dedany.cinenear.data.datasource.RegionDataSource
import com.dedany.cinenear.data.datasource.RegionDataSourceImpl
import com.dedany.cinenear.data.datasource.remote.MoviesClient
import com.dedany.cinenear.data.datasource.remote.MoviesLocalDataSource
import com.dedany.cinenear.data.datasource.remote.MoviesLocalDataSourceImpl
import com.dedany.cinenear.ui.screens.detail.DetailViewModel
import com.dedany.cinenear.ui.screens.home.HomeViewModel
import com.dedany.cinenear.usecases.FetchMoviesUseCase
import com.dedany.cinenear.usecases.FindMovieByIdUseCase
import com.dedany.cinenear.usecases.ToggleFavoriteUseCase
import com.google.android.gms.location.LocationServices

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App
    val moviesRepository = MoviesRepository(
        RegionRepository(
            RegionDataSourceImpl(
                Geocoder(app),
                LocationDataSourceimpl(
                    LocationServices.getFusedLocationProviderClient(app))
            )
        )
            ,
        MoviesLocalDataSourceImpl(app.db.moviesDao()),
        MoviesRemoteDataSourceImpl(MoviesClient.instance)
    )
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                },
                viewModel { HomeViewModel(FetchMoviesUseCase(moviesRepository)) }
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                viewModel {
                    DetailViewModel(
                        movieId,
                        FindMovieByIdUseCase(moviesRepository),
                        ToggleFavoriteUseCase(moviesRepository)
                    )
                },
                onBack = { navController.popBackStack() })
        }
    }
}