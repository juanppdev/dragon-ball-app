package com.mundocode.dragonballapp.ui.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.ui.components.CustomBottomAppBar
import com.mundocode.dragonballapp.ui.components.CustomTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GenericCharacterScreen(
    navController: NavController,
    character: DbCharacter,
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = character.name,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { CustomBottomAppBar(navController) }
    ) { paddingValues ->
        GenericCharacterContent(
            characterRemote = character,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun GenericCharacterContent(
    characterRemote: DbCharacter,
    modifier: Modifier,
) {

    var dominantColors by remember { mutableStateOf(listOf(Color.White)) }
    var mostVibrantColor by remember { mutableStateOf(Color.White) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

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
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(radius = 70.dp)
                    ) {
                        drawCircle(mostVibrantColor, radius = 150.dp.toPx())
                    }
                    coil.compose.AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(characterRemote.image)
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
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    text = characterRemote.description,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    text = "Planeta al que pertence",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF3F0F0)
@Composable
fun GenericCharacterScreenPreview() {
    GenericCharacterScreen(
        navController = rememberNavController(),
        character = DbCharacter()
    )
}

@Preview
@Composable
fun GenericCharacterContentPreview() {
    GenericCharacterContent(
        characterRemote = DbCharacter(),
        modifier = Modifier
    )
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