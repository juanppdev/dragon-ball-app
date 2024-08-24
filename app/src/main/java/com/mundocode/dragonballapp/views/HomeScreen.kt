package com.mundocode.dragonballapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.mundocode.dragonballapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.background),
        topBar = {
            TopBar(navController)
        },
        bottomBar = { BottomAppBar(navController) }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.card),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .clickable { navController.navigate("dragonBall") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Dragon Ball",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                            )

                            AsyncImage(
                                R.drawable.logo_db, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                            )
                        }
                    }
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.card),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .clickable { navController.navigate("dragonBallZ") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Dragon Ball Z",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                            AsyncImage(
                                R.drawable.logo_z, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                        }
                    }
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.card),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .clickable { navController.navigate("dragons") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = "Dragones",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                            AsyncImage(
                                R.drawable.logo_dr, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                        }
                    }
                }
            }

        }

    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.card),
            titleContentColor = Color.White,
        ),
        title = {
            Text(
                "Personajes",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { navController.navigate("favoriteScreen") }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Localized description", modifier = Modifier.size(50.dp), tint = Color.White)
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun BottomAppBar(navController: NavController) {

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
            ){
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("homeScreen") },
                    icon = { Icon(painterResource(id = R.drawable.wiki), contentDescription = "Localized description", modifier = Modifier.size(30.dp), tint = Color.White) },
                    label = { Text(text = "Wiki", color = Color.White)}
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("homeScreen") },
                    enabled = false,
                    icon = { Icon(painterResource(id = R.drawable.games), contentDescription = "Localized description", modifier = Modifier.size(30.dp), tint = Color.White) },
                    label = { Text(text = "Games", color = Color.White)}
                )
            }
        }
    }
}

@Composable
fun AsyncImage(url: Int, modifier: Modifier) {
    val painter: Painter = // Optionally, you can apply transformations
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // Optionally, you can apply transformations
                    transformations()
                }).build()
        )
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = null
    )
}