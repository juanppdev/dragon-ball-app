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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.models.Character
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import com.mundocode.dragonballapp.viewmodels.DragonBallViewModel

@Composable
fun GenericCharacterScreen(
    navController: NavController,
    character: Character,
    dragonBallType: DragonBallType,
    viewModel: DragonBallViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            CustomTopBar(title = character.name) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.background),
        bottomBar = { CustomBottomAppBar(navController) }
    ) { paddingValues ->
        GenericCharacterContent(
            dragonBallType = dragonBallType,
            character = character,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun GenericCharacterContent(
    dragonBallType: DragonBallType,
    character: Character,
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
                            .data(character.image)
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
                    text = character.description,
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