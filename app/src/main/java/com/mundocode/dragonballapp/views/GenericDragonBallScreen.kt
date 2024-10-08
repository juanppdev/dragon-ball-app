package com.mundocode.dragonballapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.models.Personaje
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.viewmodels.DragonBallViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun GenericDragonBallScreen(
    navController: NavController,
    dragonBallType: DragonBallType,
    viewModel: DragonBallViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomTopBar(
                title = when (dragonBallType) {
                    DragonBallType.DragonBall -> "Dragon Ball"
                    DragonBallType.DragonBallZ -> "Dragon Ball Z"
                    DragonBallType.DragonBallGT -> "Dragon Ball GT"
                    DragonBallType.Dragons -> "Dragons"
                },
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
        bottomBar = { CustomBottomAppBar(navController) }
    ) { paddingValues ->

        ListContent(
            list = when (dragonBallType) {
                DragonBallType.DragonBall -> state.dragonBallList
                DragonBallType.DragonBallZ -> state.dragonBallZList
                DragonBallType.DragonBallGT -> state.dragonBallGtList
                DragonBallType.Dragons -> state.dragonList
            },
            favorites = state.favoriteList,
            modifier = Modifier.padding(paddingValues),
            onItemClicked = { character ->
                navController.kiwiNavigation(
                    Destinations.PersonajeDetail(
                        dragonBallType = dragonBallType,
                        personaje = character
                    )
                )
            },
            favoriteClicked = {
                when (dragonBallType) {
                    DragonBallType.DragonBall -> viewModel.favoriteClicked(it, DragonBallType.DragonBall)
                    DragonBallType.DragonBallZ -> viewModel.favoriteClicked(it, DragonBallType.DragonBallZ)
                    DragonBallType.DragonBallGT -> viewModel.favoriteClicked(it, DragonBallType.DragonBallGT)
                    DragonBallType.Dragons -> viewModel.favoriteClicked(it, DragonBallType.Dragons)
                }
            }
        )
    }
}

@Composable
private fun ListContent(
    list: List<Personaje>,
    favorites: List<Favorite>,
    modifier: Modifier = Modifier,
    onItemClicked: (Personaje) -> Unit = {},
    favoriteClicked: (Long) -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Número de columnas
            modifier = Modifier.fillMaxSize(),
        ) {
            items(list) { item ->
                GenericCardCharacter(
                    item = item,
                    isFavorite = favorites.any { it.id == item.id },
                    onItemClicked = onItemClicked,
                    favoriteClicked = favoriteClicked
                )
            }
        }
    }
}


@Composable
fun GenericCardCharacter(
    item: Personaje,
    isFavorite: Boolean,
    onItemClicked: (Personaje) -> Unit = {},
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
            .clickable { onItemClicked(item) }
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.End
        ) {

            Row {
                Text(
                    text = item.name,
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
                        .clickable { favoriteClicked(item.id) }
                        .padding(horizontal = 8.dp)
                        .size(30.dp),
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }

            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(190.dp),
            )
        }
    }
}


//@Preview(showBackground = true, backgroundColor = 0xFF0E0F19)
//@PreviewLightDark
//@Composable
//private fun GenericDragonBallContentPreview() {
//    DragonBallAppTheme {
//        ListContent(
//            list = listOf(
//                object : BaseCharacter(
//                    id = 1,
//                    name = "name",
//                    image = "https://fastly.picsum.photos/id/959/200/300.jpg?hmac=q2WZ7w-aqWQyUVa4vEv-28yCS6TLS-M19or3y7YVvso",
//                    description = "description",
//                    biography = "biography",
//                ),
//                object : BaseCharacter(
//                    id = 1,
//                    name = "name",
//                    image = "https://fastly.picsum.photos/id/959/200/300.jpg?hmac=q2WZ7w-aqWQyUVa4vEv-28yCS6TLS-M19or3y7YVvso",
//                    description = "description",
//                    biography = "biography",
//                ),
//            ),
//            favorites = emptyList(),
//        )
//    }
//}

//@Preview(showBackground = true)
//@Composable
//private fun GenericCardCharacterPreview() {
//    DragonBallAppTheme {
//        GenericCardCharacter(
//            item = object : BaseCharacter(
//                id = 1,
//                name = "name",
//                image = "https://fastly.picsum.photos/id/959/200/300.jpg?hmac=q2WZ7w-aqWQyUVa4vEv-28yCS6TLS-M19or3y7YVvso",
//                description = "description",
//                biography = "biography",
//            ),
//            isFavorite = false
//        ),
//    }
//}