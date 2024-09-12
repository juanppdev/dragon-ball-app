package com.mundocode.dragonballapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.navigation.Destinations
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun CustomBottomAppBar(navController: NavController) {

    BottomAppBar(
        containerColor = colorResource(id = R.color.card),
        contentColor = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NavigationBar(
                containerColor = colorResource(id = R.color.card),
                contentColor = Color.White,
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.kiwiNavigation(Destinations.Home)
                    },
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.wiki),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    },
                    label = { Text(text = "Wiki", color = Color.White) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {

                    },
                    enabled = false,
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.games),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    },
                    label = { Text(text = "Games", color = Color.White) }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.kiwiNavigation(Destinations.OptionsScreen)
                    },
                    enabled = true,
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    },
                    label = { Text(text = "Games", color = Color.White) }
                )
            }
        }
    }
}