package com.mundocode.dragonballapp.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mundocode.dragonballapp.viewmodels.DragonBallZListViewModel
import com.mundocode.dragonballapp.viewmodels.FavoriteViewModel
import com.mundocode.dragonballapp.views.DragonBall
import com.mundocode.dragonballapp.views.DragonBallZ
import com.mundocode.dragonballapp.views.FavoriteScreen
import com.mundocode.dragonballapp.views.HomeScreen
import com.mundocode.dragonballapp.views.LoginScreen
import com.mundocode.dragonballapp.views.Personaje
import com.mundocode.dragonballapp.views.PersonajeZ

@Composable
fun NavManager() {
    val navController = rememberNavController()
    val viewModelF: FavoriteViewModel = viewModel()

    NavHost(navController = navController, startDestination = "loginScreen") {
        composable("loginScreen") { LoginScreen(navController = navController) }
        composable("homeScreen") { HomeScreen(navController = navController) }
        composable("dragonBallZ") {
            DragonBallZ(
                navController,
                DragonBallZListViewModel(),
                viewModelF
            )
        }
        composable("dragonBall") { DragonBall(navController = navController) }
        composable(
            "personaje/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->
                Personaje(navController = navController, id = id)
            }
        }
        composable(
            "personajeZ/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->
                PersonajeZ(navController = navController, id = id)
            }
        }
        composable("favoriteScreen") { FavoriteScreen(navController) }
    }
}