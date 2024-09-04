package com.mundocode.dragonballapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mundocode.dragonballapp.views.FavoriteScreen
import com.mundocode.dragonballapp.views.GenericCharacterScreen
import com.mundocode.dragonballapp.views.GenericDragonBallScreen
import com.mundocode.dragonballapp.views.HomeScreen
import com.mundocode.dragonballapp.views.LoginScreen

@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "loginScreen",
    ) {
        composable<Login> {
            LoginScreen(navController = navController)
        }

        composable<Home> {
            HomeScreen(navController = navController)
        }

        composable<PersonajeList> { backStackEntry ->
            val listScreen: PersonajeList = backStackEntry.toRoute()

            GenericDragonBallScreen(
                navController = navController,
                dragonBallType = listScreen.dragonBallType
            )
        }

        composable<PersonajeDetail> { backStackEntry ->
            val personajeDetail: PersonajeDetail = backStackEntry.toRoute()

            GenericCharacterScreen(
                navController = navController,
                personaje = personajeDetail.personaje,
                dragonBallType = personajeDetail.dragonBallType,
            )
        }

        composable("favoriteScreen") {
            FavoriteScreen(navController)
        }
    }
}