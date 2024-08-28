package com.mundocode.dragonballapp.views

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.mundocode.dragonball.models.SingleDragonBallZLista
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.viewmodels.UnifiedDragonBallViewModel
import com.mundocode.dragonballapp.viewmodels.UnifiedDragonBallViewModelFactory

@Composable
fun PersonajeZ(
    navController: NavController,
    id: Long
) {
    // Crear el ViewModel con el tipo y ID adecuados
    val viewModel: UnifiedDragonBallViewModel = viewModel(
        factory = UnifiedDragonBallViewModelFactory(DragonBallType.SAIYAN_Z, id)
    )

    // Obtener detalles del personaje
    LaunchedEffect(id) {
        viewModel.getDetails(DragonBallType.SAIYAN_Z, id)
    }

    val dragonDetails by viewModel.detailsZ.collectAsState()

    Content(
        dragonDetails = dragonDetails,
        navController = navController
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    dragonDetails: SingleDragonBallZLista?,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        var dominantColors by remember { mutableStateOf(listOf(Color.White)) }
        var mostVibrantColor by remember { mutableStateOf(Color.White) }
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }

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
                        dragonDetails?.let { details ->
                            Text(
                                details.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("dragonBallZ") }) {
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
            Box(modifier = Modifier.padding(innerPadding)) {
                dragonDetails?.let { details ->
                    LazyColumn(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp), contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize().blur(radius = 70.dp)) {
                                    drawCircle(mostVibrantColor, radius = 150.dp.toPx())
                                }
                                coil.compose.AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(details.image)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize(),
                                    onSuccess = { result ->
                                        bitmap = result.result.drawable.toBitmap()
                                    }
                                )
                            }

                            bitmap?.let {
                                LaunchedEffect(it) {
                                    detectColors(it) { colors, vibrantColor ->
                                        dominantColors = colors
                                        mostVibrantColor = vibrantColor
                                    }
                                }
                            }

                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth(),
                                text = "Descripcion",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                fontSize = 24.sp,
                                color = Color.White
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth(),
                                text = details.description,
                                textAlign = TextAlign.Start,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth(),
                                text = "Planeta al que pertence",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                fontSize = 24.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}