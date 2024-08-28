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
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.viewmodels.UnifiedDragonBallViewModel
import com.mundocode.dragonballapp.viewmodels.UnifiedDragonBallViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Personaje(
    navController: NavController,
    id: Long
) {
    // Crear el ViewModel con el tipo y ID adecuados
    val viewModel: UnifiedDragonBallViewModel = viewModel(
        factory = UnifiedDragonBallViewModelFactory(DragonBallType.SAIYAN, id)
    )

    // Obtener detalles del personaje
    LaunchedEffect(id) {
        viewModel.getDetails(DragonBallType.SAIYAN, id)
    }

    val dragonDetails by viewModel.details.collectAsState()

    Content(
        dragonDetails = dragonDetails,
        navController = navController
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    dragonDetails: SingleDragonBallLista?,
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
                        IconButton(onClick = { navController.navigate("dragonBall") }) {
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


suspend fun detectColors(bitmap: Bitmap, onComplete: (List<Color>, Color) -> Unit) {
    withContext(Dispatchers.Default) {
        // Convertir el bitmap a ARGB_8888 si es necesario
        val convertedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val resizedBitmap = Bitmap.createScaledBitmap(convertedBitmap, 50, 50, true)
        val colorCounts = mutableMapOf<Int, Int>()

        for (y in 0 until resizedBitmap.height) {
            for (x in 0 until resizedBitmap.width) {
                val pixel = resizedBitmap.getPixel(x, y)
                colorCounts[pixel] = colorCounts.getOrDefault(pixel, 0) + 1
            }
        }

        val colorClusters = clusterColors(colorCounts, 5)
        val vibrantColor = findMostVibrantColor(colorClusters)
        val colors = colorClusters.map { Color(it) }

        onComplete(colors, Color(vibrantColor))
    }
}


fun clusterColors(colorCounts: Map<Int, Int>, maxClusters: Int): List<Int> {
    val clusters = mutableListOf<Int>()

    for ((color, _) in colorCounts) {
        if (clusters.size < maxClusters) {
            clusters.add(color)
        } else {
            var minDistance = Float.MAX_VALUE
            var closestClusterIndex = 0
            for ((index, clusterColor) in clusters.withIndex()) {
                val distance = colorDistance(color, clusterColor)
                if (distance < minDistance) {
                    minDistance = distance
                    closestClusterIndex = index
                }
            }
            clusters[closestClusterIndex] = mixColors(clusters[closestClusterIndex], color)
        }
    }

    return clusters
}

fun findMostVibrantColor(colors: List<Int>): Int {
    return colors.maxByOrNull { colorSaturation(it) } ?: android.graphics.Color.WHITE
}

fun colorSaturation(color: Int): Float {
    val r = android.graphics.Color.red(color) / 255.0f
    val g = android.graphics.Color.green(color) / 255.0f
    val b = android.graphics.Color.blue(color) / 255.0f

    val maxColor = maxOf(r, g, b)
    val minColor = minOf(r, g, b)
    return maxColor - minColor
}

fun colorDistance(color1: Int, color2: Int): Float {
    val r1 = android.graphics.Color.red(color1) / 255.0f
    val g1 = android.graphics.Color.green(color1) / 255.0f
    val b1 = android.graphics.Color.blue(color1) / 255.0f
    val r2 = android.graphics.Color.red(color2) / 255.0f
    val g2 = android.graphics.Color.green(color2) / 255.0f
    val b2 = android.graphics.Color.blue(color2) / 255.0f

    val dr = r1 - r2
    val dg = g1 - g2
    val db = b1 - b2

    return dr * dr + dg * dg + db * db
}

fun mixColors(color1: Int, color2: Int): Int {
    val r1 = android.graphics.Color.red(color1) / 255.0f
    val g1 = android.graphics.Color.green(color1) / 255.0f
    val b1 = android.graphics.Color.blue(color1) / 255.0f
    val r2 = android.graphics.Color.red(color2) / 255.0f
    val g2 = android.graphics.Color.green(color2) / 255.0f
    val b2 = android.graphics.Color.blue(color2) / 255.0f

    val r = (r1 + r2) / 2
    val g = (g1 + g2) / 2
    val b = (b1 + b2) / 2

    return android.graphics.Color.rgb(r, g, b)
}
