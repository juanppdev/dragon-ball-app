package com.mundocode.dragonballapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.mundocode.dragonballapp.ui.screens.GenericCharacterScreen
import com.mundocode.dragonballapp.ui.screens.HomeScreen
import com.mundocode.dragonballapp.ui.screens.favorite.FavoriteScreen
import com.mundocode.dragonballapp.ui.screens.list.DragonBallListScreen
import com.mundocode.dragonballapp.ui.screens.login.LoginScreen
import com.mundocode.dragonballapp.ui.screens.settings.SettingsScreen
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

        composable<Destinations.CharacterList> {
            DragonBallListScreen(
                navController = navController,
                dragonBallType = dragonBallType
            )
        }

        composable<Destinations.CharacterDetail> {
            GenericCharacterScreen(
                navController = navController,
                character = character
            )
        }

        composable<Destinations.Favorite> {
            FavoriteScreen(
                navController = navController
            )
        }

        composable<Destinations.OptionsScreen> {
            SettingsScreen(
                navController = navController
            )
        }
    }
}