package com.mundocode.dragonballapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.models.types.DragonBallType
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.ui.components.CustomBottomAppBar
import com.mundocode.dragonballapp.ui.components.CustomTopBar
import com.mundocode.dragonballapp.ui.theme.DragonBallAppTheme
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Personajes",
                actions = {
                    IconButton(onClick = {
                        navController.kiwiNavigation(Destinations.Favorite)
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        bottomBar = { CustomBottomAppBar(navController) },
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(25.dp)
        ) {
            item {

                PersonajesListItem(
                    title = "Dragon Ball",
                    icon = R.drawable.logo_db,
                    onClick = {
                        navController.kiwiNavigation(Destinations.CharacterList(DragonBallType.DragonBall))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragon Ball Z",
                    icon = R.drawable.logo_z,
                    onClick = {
                        navController.kiwiNavigation(Destinations.CharacterList(DragonBallType.DragonBallZ))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragon Ball GT",
                    icon = R.drawable.logo_gt,
                    onClick = {
                        navController.kiwiNavigation(Destinations.CharacterList(DragonBallType.DragonBallGT))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragon Ball Super",
                    icon = R.drawable.logo_super,
                    onClick = {
                        navController.kiwiNavigation(Destinations.CharacterList(DragonBallType.DragonBallSuper))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragones",
                    icon = R.drawable.logo_dr,
                    iconSize = 60.dp,
                    onClick = {
                        navController.kiwiNavigation(Destinations.CharacterList(DragonBallType.Dragons))
                    }
                )
            }
        }
    }
}

@Composable
private fun PersonajesListItem(
    title: String,
    @DrawableRes icon: Int,
    iconSize: Dp = 70.dp,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 30.sp,
                modifier = Modifier
                    .weight(0.6f),
                textAlign = TextAlign.Center,
            )

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .height(iconSize),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF3F0F0)
@Composable
fun HomeScreenPreview() {
    DragonBallAppTheme(darkTheme = false) {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF3F0F0)
@Composable
fun PersonajesListItemPreview() {
    DragonBallAppTheme(darkTheme = false) {
        PersonajesListItem(
            title = "DragonBallZ",
            icon = R.drawable.logo_z,
            onClick = { }
        )
    }
}