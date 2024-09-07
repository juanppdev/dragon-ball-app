package com.mundocode.dragonballapp.views

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kiwi.navigationcompose.typed.navigate
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.ui.theme.DragonBallAppTheme
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Personajes",
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Destinations.FavoriteScreen)
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        bottomBar = { CustomBottomAppBar(navController) },
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.background),
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
                        navController.navigate(Destinations.PersonajeList(DragonBallType.DragonBall))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragon Ball Z",
                    icon = R.drawable.logo_z,
                    onClick = {
                        navController.navigate(Destinations.PersonajeList(DragonBallType.DragonBallZ))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragon Ball GT",
                    icon = R.drawable.logo_gt,
                    onClick = {
                        navController.navigate(Destinations.PersonajeList(DragonBallType.DragonBallGT))
                    }
                )
            }

            item {
                PersonajesListItem(
                    title = "Dragones",
                    icon = R.drawable.logo_dr,
                    iconSize = 60.dp,
                    onClick = {
                        navController.navigate(Destinations.PersonajeList(DragonBallType.Dragons))
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
            containerColor = colorResource(id = R.color.card),
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
    DragonBallAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF3F0F0)
@Composable
fun PersonajesListItemPreview() {
    DragonBallAppTheme {
        PersonajesListItem(
            title = "DragonBallZ",
            icon = R.drawable.logo_z,
            onClick = { }
        )
    }
}