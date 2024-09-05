package com.mundocode.dragonballapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kiwi.navigationcompose.typed.navigate
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.viewmodels.DragonBallViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: DragonBallViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(title = "Favoritos") {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        bottomBar = { CustomBottomAppBar(navController) },
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.background),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(state.favoriteList) { favorite ->
                when (favorite.type) {
                    DragonBallType.DragonBall -> {
                        val item = state.dragonBallList.find { it.id == favorite.id }
                        if (item != null) {
                            FavoriteItemCard(
                                favorite = favorite,
                                isFavorite = state.favoriteList.any { it.id == favorite.id },
                                getItem = item.id,
                                imagePainter = item.image,
                                itemName = item.name,
                                onItemClicked = {
                                    navController.navigate(
                                        Destinations.PersonajeDetail(
                                            dragonBallType = DragonBallType.DragonBall,
                                            personaje = item
                                        )
                                    )
                                },
                                favoriteClicked = {
                                    viewModel.favoriteClicked(it, DragonBallType.DragonBall)
                                }
                            )
                        }
                    }

                    DragonBallType.DragonBallZ -> {
                        val item = state.dragonBallZList.find { it.id == favorite.id }
                        if (item != null) {
                            FavoriteItemCard(
                                favorite = favorite,
                                isFavorite = state.favoriteList.any { it.id == favorite.id },
                                getItem = item.id,
                                imagePainter = item.image,
                                itemName = item.name,
                                onItemClicked = {
                                    navController.navigate(
                                        Destinations.PersonajeDetail(
                                            dragonBallType = DragonBallType.DragonBallZ,
                                            personaje = item
                                        )
                                    )
                                },
                                favoriteClicked = {
                                    viewModel.favoriteClicked(it, DragonBallType.DragonBallZ)
                                }
                            )
                        }
                    }

                    DragonBallType.Dragons -> {
                        val item = state.dragonList.find { it.id == favorite.id }
                        if (item != null) {
                            FavoriteItemCard(
                                favorite = favorite,
                                isFavorite = state.favoriteList.any { it.id == favorite.id },
                                getItem = item.id,
                                imagePainter = item.image,
                                itemName = item.name,
                                onItemClicked = {
                                    navController.navigate(
                                        Destinations.PersonajeDetail(
                                            dragonBallType = DragonBallType.Dragons,
                                            personaje = item
                                        )
                                    )
                                },
                                favoriteClicked = {
                                    viewModel.favoriteClicked(it, DragonBallType.Dragons)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItemCard(
    favorite: Favorite,
    isFavorite: Boolean,
    getItem: (Long),
    imagePainter: String,
    itemName: String,
    onItemClicked: (Long) -> Unit = {},
    favoriteClicked: (Long) -> Unit = {},
) {

    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClicked(favorite.id) }
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.End
        ) {

            Row {
                Text(
                    text = itemName,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = true,
                    minLines = 3,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    modifier = Modifier
                        .clickable { favoriteClicked(getItem) }
                        .padding(horizontal = 8.dp)
                        .size(30.dp),
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }

            Image(
                painter = rememberAsyncImagePainter(model = imagePainter),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp),
            )
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    FavoriteItemCard(
        favorite = Favorite(1, DragonBallType.DragonBall),
        isFavorite = true,
        getItem = 1,
        imagePainter = "https://fastly.picsum.photos/id/959/200/300.jpg?hmac=q2WZ7w-aqWQyUVa4vEv-28yCS6TLS-M19or3y7YVvso",
        itemName = "Goku",
    )
}