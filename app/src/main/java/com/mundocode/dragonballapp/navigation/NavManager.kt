package com.mundocode.dragonballapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.views.FavoriteScreen
import com.mundocode.dragonballapp.views.GenericDragonBallScreen
import com.mundocode.dragonballapp.views.HomeScreen
import com.mundocode.dragonballapp.views.LoginScreen
import com.mundocode.dragonballapp.views.Personaje
import com.mundocode.dragonballapp.views.PersonajeDragons
import com.mundocode.dragonballapp.views.PersonajeZ

@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "loginScreen",
    ) {
        composable("loginScreen") { LoginScreen(navController = navController) }
        composable("homeScreen") { HomeScreen(navController = navController) }

        composable("genericDragonBallScreen/{dragonBallType}", arguments = listOf(navArgument("dragonBallType") { type = NavType.IntType })) {
            it.arguments?.getInt("dragonBallType")?.let { dragonBallInt ->
                GenericDragonBallScreen(navController = navController, dragonBallType = DragonBallType.entries[dragonBallInt])
            }
        }

        composable("personaje/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) {
            it.arguments?.getLong("id")?.let { id ->
                Personaje(navController = navController, id = id)
            }
        }
        composable("personajeZ/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) {
            it.arguments?.getLong("id")?.let { id ->
                PersonajeZ(navController = navController, id = id)
            }
        }
        composable("personajeDragons/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) {
            it.arguments?.getLong("id")?.let { id ->
                PersonajeDragons(navController = navController, id = id)
            }
        }
        composable("favoriteScreen") {
            FavoriteScreen(navController)
        }
    }
}