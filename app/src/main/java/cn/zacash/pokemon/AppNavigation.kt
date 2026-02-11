package cn.zacash.pokemon

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cn.zacash.pokemon.Screen.Detail
import cn.zacash.pokemon.Screen.Home
import cn.zacash.pokemon.Screen.Splash
import cn.zacash.pokemon.ui.screen.DetailScreen
import cn.zacash.pokemon.ui.screen.HomeScreen
import cn.zacash.pokemon.ui.screen.SplashScreen
import cn.zacash.pokemon.viewmodel.PokemonViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Detail : Screen("detail")
}

@Composable
fun AppNavHost(navController: NavHostController, isFirstLaunch: Boolean) {
    val startRoute = if (isFirstLaunch) Splash.route else Home.route
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(Splash.route) {
            SplashScreen(onNavToHome = {
                navController.navigate(Home.route) {
                    // not back to splash again
                    popUpTo(Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(Home.route) { entry ->
            val viewModel: PokemonViewModel = viewModel(entry)
            HomeScreen(onNavToDetail = {
                navController.navigate(Detail.route)
            }, viewModel)
        }
        composable(Detail.route) {
            // use the home viewmodel instance to share pokemon data
            navController.previousBackStackEntry?.let { entry ->
                val viewModel: PokemonViewModel = viewModel(entry)
                DetailScreen(onBack = {
                    navController.popBackStack()
                }, viewModel)
            }
        }
    }
}