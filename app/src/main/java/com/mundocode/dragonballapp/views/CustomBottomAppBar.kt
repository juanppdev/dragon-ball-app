package com.mundocode.dragonballapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.navigation.Destinations
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun CustomBottomAppBar(navController: NavController) {

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
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
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Wiki", color = MaterialTheme.colorScheme.onSurface) }
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
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Games", color = MaterialTheme.colorScheme.onSurface) }
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
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    label = { Text(text = "Games", color = MaterialTheme.colorScheme.onSurface) }
                )
            }
        }
    }
}