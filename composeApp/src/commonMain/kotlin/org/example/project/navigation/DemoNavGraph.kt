package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.ui.HomeScreen
import org.example.project.ui.HomeViewModel
import org.example.project.ui.MovieScreen
import org.example.project.ui.MovieViewModel
import org.koin.compose.currentKoinScope

@Composable
fun DemoNavGraph(
    modifier:  Modifier = Modifier,
    startDestination : DemoDestinations = DemoDestinations.Home,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        startDestination = startDestination.route,
        navController = navController
    ) {
        composable(DemoDestinations.Home.route) {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToMovie = { navController.navigate("movie/$it") }
            )
        }

        composable(
            DemoDestinations.Movie.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType})
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            val viewModel = koinViewModel<MovieViewModel>()

            MovieScreen(
                id = id,
                viewModel = viewModel,
                onBackClick = navController::popBackStack
            )
        }
    }
}


@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}