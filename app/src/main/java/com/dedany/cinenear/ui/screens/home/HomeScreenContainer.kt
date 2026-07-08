package com.dedany.cinenear.ui.screens.home

import android.Manifest
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dedany.cinenear.R
import com.dedany.cinenear.ui.common.PermissionRequestEffect
import com.dedany.cinenear.ui.theme.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContainer(
    onMovieClick: (Int) -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    val homeState = rememberHomeState()

    PermissionRequestEffect(Manifest.permission.ACCESS_COARSE_LOCATION) { _ ->
        vm.onUiReady()
    }

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    scrollBehavior = homeState.scrollBehavior
                )
            }
        ) { padding ->
            HomeScreen(
                state = state,
                onFilterSelected = vm::onFilterSelected,
                onMovieClick = onMovieClick,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
