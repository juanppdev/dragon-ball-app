package com.mundocode.dragonballapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.viewmodels.DragonBallViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragonBall(
    navController: NavController,
    viewModel: DragonBallViewModel = viewModel(),
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.background),
        topBar = {
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
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("homeScreen") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        bottomBar = { CustomBottomAppBar(navController) }
    ) { paddingValues ->
        DragonBallContent(
            list = state.dragonBallList,
            modifier = Modifier.padding(paddingValues),
            onItemClicked = { id ->
                navController.navigate("personaje/$id")
            }
        )
    }
}

@Composable
private fun DragonBallContent(
    list: List<DragonBallLista>,
    modifier: Modifier = Modifier,
    onItemClicked: (Long) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Número de columnas
            modifier = Modifier.fillMaxSize(),
        ) {
            items(list) { item ->
                CardPersonaje(
                    item = item,
                    onItemClicked = onItemClicked,
                )
            }
        }
    }
}


@Composable
fun CardPersonaje(
    item: DragonBallLista,
    onItemClicked: (Long) -> Unit,
) {

    val scale by remember { mutableFloatStateOf(2f) }
    val offsetX by remember { mutableFloatStateOf(0f) }
    val offsetY by remember { mutableFloatStateOf(200f) }


    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card),
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onItemClicked(item.id) }
    ) {
        // Estado para controlar si es favorito
        val isFavorite = remember { mutableStateOf(false) }

        // Observar los favoritos desde el ViewModel
//        val favorites by viewModel.allFavorites.observeAsState(emptyList())

        // Actualizar el estado cuando cambien los favoritos
//        LaunchedEffect(favorites) {
//            isFavorite.value = favorites.any { it.id == id }
//        }

        Column {
            Icon(
                modifier = Modifier
                    .clickable {
//                        if (isFavorite.value) {
//                            viewModel.removeFavorite(Favorite(id = id, type = DragonBallType.SAIYAN))
//                        } else {
//                            viewModel.addFavorite(Favorite(id = id, type = DragonBallType.SAIYAN))
//                        }
                    },
                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite.value) Color.Red else Color.Gray
            )
            Box {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = item.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.name.replace(" ", "\n"),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        softWrap = true,
                        style = TextStyle(
                            lineBreak = LineBreak.Paragraph.copy(strictness = LineBreak.Strictness.Loose)
                        )
                    )
                }
            }
        }
    }
}