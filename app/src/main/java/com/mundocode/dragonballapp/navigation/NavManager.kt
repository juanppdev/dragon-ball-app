package com.mundocode.dragonballapp.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mundocode.dragonballapp.models.Character
import com.mundocode.dragonballapp.viewmodels.DragonBallType
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
        composable("loginScreen") { LoginScreen(navController = navController) }
        composable("homeScreen") { HomeScreen(navController = navController) }

        composable(
            "genericDragonBallScreen/{dragonBallType}",
            arguments = listOf(navArgument("dragonBallType") { type = NavType.IntType })
        ) {
            it.arguments?.getInt("dragonBallType")?.let { dragonBallInt ->
                GenericDragonBallScreen(
                    navController = navController,
                    dragonBallType = DragonBallType.entries[dragonBallInt]
                )
            }
        }

        composable(
            "genericCharacterBallScreen/{dragonBallType}/{character}",
            arguments = listOf(
                navArgument("dragonBallType") {
                    type = NavType.IntType
                },
                navArgument("character") {
                    type = NavType.ParcelableType(Character::class.java)
                }
            )
        ) {
            val dragonBallType = it.arguments?.getInt("dragonBallType")?.let { dragonBallInt ->
                DragonBallType.entries[dragonBallInt]
            }

            val character = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.arguments?.getParcelable("character", Character::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.arguments?.getParcelable("character") as? Character
            }

            GenericCharacterScreen(
                navController = navController,
                character = character!!,
                dragonBallType = dragonBallType!!,
            )
        }

//        composable("personaje/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) {
//            it.arguments?.getLong("id")?.let { id ->
//                Personaje(navController = navController, id = id)
//            }
//        }
//        composable("personajeZ/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) {
//            it.arguments?.getLong("id")?.let { id ->
//                PersonajeZ(navController = navController, id = id)
//            }
//        }
//        composable("personajeDragons/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) {
//            it.arguments?.getLong("id")?.let { id ->
//                PersonajeDragons(navController = navController, id = id)
//            }
//        }
        composable("favoriteScreen") {
            FavoriteScreen(navController)
        }
    }
}