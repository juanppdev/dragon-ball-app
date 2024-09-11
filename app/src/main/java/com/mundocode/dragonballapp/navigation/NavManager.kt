package com.mundocode.dragonballapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.mundocode.dragonballapp.views.FavoriteScreen
import com.mundocode.dragonballapp.views.GenericCharacterScreen
import com.mundocode.dragonballapp.views.GenericDragonBallScreen
import com.mundocode.dragonballapp.views.HomeScreen
import com.mundocode.dragonballapp.views.LoginScreen
import com.mundocode.dragonballapp.views.OptionsScreen
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

        composable<Destinations.PersonajeList> {
            GenericDragonBallScreen(
                navController = navController,
                dragonBallType = dragonBallType
            )
        }

        composable<Destinations.PersonajeDetail> {
            GenericCharacterScreen(
                navController = navController,
                personaje = personaje,
                dragonBallType = dragonBallType,
            )
        }

        composable<Destinations.FavoriteScreen> {
            FavoriteScreen(
                navController = navController
            )
        }

        composable<Destinations.OptionsScreen> {
            OptionsScreen(
                navController = navController
            )
        }
    }
}