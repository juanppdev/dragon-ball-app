package com.mundocode.dragonballapp.ui.screens.list

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.models.types.DragonBallType
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.ui.components.CustomBottomAppBar
import com.mundocode.dragonballapp.ui.components.CustomProgressIndicator
import com.mundocode.dragonballapp.ui.components.CustomTopBar
import com.mundocode.dragonballapp.ui.theme.DragonBallAppTheme
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun DragonBallListScreen(
    navController: NavController,
    dragonBallType: DragonBallType,
    viewModel: DragonBallViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.getCharacterList(dragonBallType)
    }

    LaunchedEffect(state.error) {
        state.error?.let { errorMessage ->
            snackbarHostState.showSnackbar(
                message = errorMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Short,
            )
        }
    }

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
                    DragonBallType.DragonBallSuper -> "Dragon Ball Super"
                    DragonBallType.Dragons -> "Dragons"
                    DragonBallType.Favorites -> "Favoritos"
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
        bottomBar = { CustomBottomAppBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        DragonBallListContent(
            list = state.characterList,
            modifier = Modifier.padding(paddingValues),
            onItemClicked = { character ->
                navController.kiwiNavigation(
                    Destinations.CharacterDetail(
                        dragonBallType = dragonBallType,
                        characterRemote = character
                    )
                )
            },
            favoriteClicked = { character ->
                viewModel.favoriteClicked(character, dragonBallType)
            }
        )

        CustomProgressIndicator(isVisible = state.isLoading)
    }
}

@Composable
private fun DragonBallListContent(
    list: List<DbCharacter>,
    modifier: Modifier = Modifier,
    onItemClicked: (DbCharacter) -> Unit = {},
    favoriteClicked: (DbCharacter) -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Número de columnas
            modifier = Modifier.fillMaxSize(),
        ) {
            items(list) { item ->
                GenericCardCharacter(
                    item = item,
                    onItemClicked = onItemClicked,
                    favoriteClicked = favoriteClicked
                )
            }
        }
    }
}

@Composable
fun GenericCardCharacter(
    item: DbCharacter,
    onItemClicked: (DbCharacter) -> Unit = {},
    favoriteClicked: (DbCharacter) -> Unit = {},
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
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    modifier = Modifier
                        .clickable { favoriteClicked(item) }
                        .padding(horizontal = 8.dp)
                        .size(30.dp),
                    imageVector = if (item.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (item.isFavorite) Color.Red else Color.Gray
                )
            }

            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(0.dp)
                    .size(190.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DragonBallListContentPreview() {
    DragonBallAppTheme {
        DragonBallListContent(
            list = listOf(
                DbCharacter().copy(
                    id = 1,
                    name = "Goku",
                    image = "https://i.pinimg.com/originals/7b/7b/7b/"
                ),
                DbCharacter().copy(
                    id = 2,
                    name = "Vegeta",
                    image = "https://i.pinimg.com/originals/7b/7b/7b/",
                    isFavorite = true,
                ),
                DbCharacter().copy(
                    id = 3,
                    name = "Gohan",
                    image = "https://i.pinimg.com/originals/7b/7b/7b/"
                ),
            ),
        )
    }
}