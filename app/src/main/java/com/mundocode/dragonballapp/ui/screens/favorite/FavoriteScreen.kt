package com.mundocode.dragonballapp.ui.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.ui.components.CustomBottomAppBar
import com.mundocode.dragonballapp.ui.components.CustomTopBar
import com.mundocode.dragonballapp.ui.screens.list.DragonBallListContent
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    val state by viewModel.backendState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Favoritos",
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        bottomBar = { CustomBottomAppBar(navController) },
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->

        DragonBallListContent(
            list = state.favoriteList,
            modifier = Modifier.padding(paddingValues),
            onItemClicked = { character ->
                navController.kiwiNavigation(
                    Destinations.CharacterDetail(character = character)
                )
            },
            favoriteClicked = { character ->
                viewModel.removeFavorite(character)
            }
        )
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
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
