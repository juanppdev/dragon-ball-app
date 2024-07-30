package com.mundocode.dragonballapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mundocode.dragonballapp.viewmodels.DragonBallListViewModel
import com.mundocode.dragonballapp.views.DragonBall
import com.mundocode.dragonballapp.views.HomeScreen
import com.mundocode.dragonballapp.views.LoginScreen

@Composable
fun NavManager() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginScreen") {
        composable("loginScreen") { LoginScreen(navController = navController) }
        composable("homeScreen") { HomeScreen(navController = navController) }
        composable("dragonBall") { DragonBall(navController = navController, viewModel = DragonBallListViewModel()) }
        //composable("dragonBallZ") { dragonBallZ(navController = navController, viewModel = DragonBallListViewModel()) }
        //composable("dragones") { dragones(navController = navController, viewModel = DragonBallListViewModel()) }
    }

}