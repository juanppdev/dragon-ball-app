package com.mundocode.dragonballapp.views

import android.util.Log
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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.viewmodels.FavoriteViewModel
import com.mundocode.dragonballapp.viewmodels.UnifiedDragonBallViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    navController: NavController,
    modelView: UnifiedDragonBallViewModel,
    modelZView: UnifiedDragonBallViewModel,
    modelDView: UnifiedDragonBallViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val favorites by viewModel.allFavorites.observeAsState(emptyList())

    val list by modelView.list.collectAsState()
    val listZ by modelZView.listZ.collectAsState()
    val listD by modelDView.listD.collectAsState()

    LaunchedEffect(Unit) {
        // Verifica si los datos están cargados o llama a cargar datos aquí si es necesario
        if (list == null) modelView.getList(DragonBallType.SAIYAN)
        if (listZ == null) modelZView.getList(DragonBallType.SAIYAN_Z)
        if (listD == null) modelDView.getList(DragonBallType.DRAGONS)
    }

    Log.d("FavoriteScreen", "Favorites: $favorites")
    Log.d("FavoriteScreen", "List: $list")
    Log.d("FavoriteScreen", "ListZ: $listZ")
    Log.d("FavoriteScreen", "ListD: $listD")



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                            "Favoritos",
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
                    scrollBehavior = scrollBehavior,
                )
            },
            bottomBar = { BottomAppBar(navController) }
        ) { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                items(favorites) { favorite ->
                    when (favorite.type) {
                        DragonBallType.SAIYAN -> {
                            val item = list?.find { it.id == favorite.id }
                            item?.let {
                                FavoriteItemCard(
                                    favorite,
                                    navController,
                                    { id -> list?.find { it.id == id } },
                                    {
                                        rememberAsyncImagePainter(model = item.image)
                                    },
                                    it.name,
                                    viewModel
                                )
                            }
                        }
                        DragonBallType.SAIYAN_Z -> {
                            val item = listZ?.find { it.id == favorite.id }
                            item?.let {
                                FavoriteItemCard(
                                    favorite,
                                    navController,
                                    { id -> listZ?.find { it.id == id } },
                                    { rememberAsyncImagePainter(model = item.image) },
                                    it.name,
                                    viewModel
                                )
                            }
                        }
                        DragonBallType.DRAGONS -> {
                            val item = listD?.find { it.id == favorite.id }
                            item?.let { it ->
                                FavoriteItemCard(
                                    favorite,
                                    navController,
                                    { id -> listD?.find { it.id == id } },
                                    { rememberAsyncImagePainter(model = item.image) },
                                    it.name,
                                    viewModel
                                )
                            }
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
    navController: NavController,
    getItem: (Long) -> Any?,
    imagePainter: @Composable (Any) -> Painter,
    itemName: String,
    viewModel: FavoriteViewModel
) {
    val scale by remember { mutableFloatStateOf(2f) }
    val offsetX by remember { mutableFloatStateOf(0f) }
    val offsetY by remember { mutableFloatStateOf(200f) }

    val isFavorite = remember { mutableStateOf(false) }
    val favorites by viewModel.allFavorites.observeAsState(emptyList())

    LaunchedEffect(favorites) {
        isFavorite.value = favorites.any { it.id == favorite.id }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card),
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable {
                navController.navigate("personaje/${favorite.id}")
            }
    ) {
        Column {
            Icon(
                modifier = Modifier
                    .clickable {
                        if (isFavorite.value) {
                            viewModel.removeFavorite(Favorite(id = favorite.id, type = favorite.type))
                        } else {
                            viewModel.addFavorite(Favorite(id = favorite.id, type = favorite.type))
                        }
                    }
                    .padding(8.dp),
                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite.value) Color.Red else Color.Gray
            )

            val item = getItem(favorite.id)
            item?.let {
                Box {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Image(
                            painter = imagePainter(it),
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
                            text = itemName.replace(" ", "\n"),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            softWrap = true
                        )
                    }
                }
            }
        }
    }
}
