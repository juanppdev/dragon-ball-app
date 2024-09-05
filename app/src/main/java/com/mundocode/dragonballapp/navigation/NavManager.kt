package com.mundocode.dragonballapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kiwi.navigationcompose.typed.Destination
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.mundocode.dragonballapp.views.FavoriteScreen
import com.mundocode.dragonballapp.views.GenericCharacterScreen
import com.mundocode.dragonballapp.views.GenericDragonBallScreen
import com.mundocode.dragonballapp.views.HomeScreen
import com.mundocode.dragonballapp.views.LoginScreen
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = createRoutePattern<Destinations.Login>(),
    ) {
        composable<Destinations.Login> {
            LoginScreen(navController = navController)
        }

        composable<Destinations.Home> {
            HomeScreen(navController = navController)
        }

        composable<Destinations.PersonajeList> { backStackEntry ->
            val listScreen: Destinations.PersonajeList = backStackEntry.toRoute()

            GenericDragonBallScreen(
                navController = navController,
                dragonBallType = listScreen.dragonBallType
            )
        }

        composable<Destinations.PersonajeDetail> { backStackEntry ->
            val personajeDetail: Destinations.PersonajeDetail = backStackEntry.toRoute()

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